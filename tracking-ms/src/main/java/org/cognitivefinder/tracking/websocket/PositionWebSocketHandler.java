package org.cognitivefinder.tracking.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.cognitivefinder.tracking.gps.position.PositionResponseTRC;
import org.cognitivefinder.tracking.modules.device.Device;
import org.cognitivefinder.tracking.modules.device.DeviceRepository;
import org.cognitivefinder.tracking.modules.patient.PatientService;
import org.cognitivefinder.tracking.security.AuthService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
@RequiredArgsConstructor
public class PositionWebSocketHandler extends TextWebSocketHandler {

    private WebSocketSession session;
    private final AuthService authService;
    private final PatientService patientService;
    private final DeviceRepository deviceRepository;
    private final List<Long> deviceGpsIds = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
        log.info("WebSocket connection established.");

        // Retrieve the authenticated user ID from the WebSocket session attributes
        String clientId = (String) session.getAttributes().get("userId");
        if (clientId == null) {
            log.error("User ID (sub) not found in the WebSocket session attributes.");
            session.close();
            return;
        }

        log.info("Authenticated client ID: {}", clientId);
        String token = (String) session.getAttributes().get("token");
        String authHeader = "Bearer " + token;
        // Request the list of patient IDs from the patient microservice
        List<String> patientIds = patientService.fetchPatientIdsByClientId(authHeader,clientId);
        log.info("Fetched patient IDs: {}", patientIds);

        // Retrieve the list of devices matching patient IDs
        List<Device> devices = deviceRepository.findByPatientIdIn(patientIds);
        if (devices != null && !devices.isEmpty()) {
            deviceGpsIds.clear(); // Ensure no duplicates from previous connections
            deviceGpsIds.addAll(devices.stream().map(Device::getTraccarId).collect(Collectors.toList()));
            log.info("Device GPS IDs: {}", deviceGpsIds);
        } else {
            log.warn("No devices found for the given patient IDs.");
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("Received message: {}", message.getPayload());
        // Handle incoming WebSocket messages if required
    }

    public void sendPositionToClient(List<PositionResponseTRC> positions) {
        if (session == null || !session.isOpen()) {
            log.warn("WebSocket session is not open. Unable to send positions.");
            return;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(positions);
            session.sendMessage(new TextMessage(jsonMessage));
            log.info("Sent positions to client: {}", jsonMessage);
        } catch (Exception e) {
            log.error("Failed to send positions to client: {}", e.getMessage(), e);
        }
    }

    public List<Long> getDeviceGpsIds() {
        return deviceGpsIds;
    }
}

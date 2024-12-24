package org.cognitivefinder.tracking.modules.track;

import java.util.ArrayList;
import java.util.List;

import org.cognitivefinder.tracking.gps.position.GPSPositionService;
import org.cognitivefinder.tracking.gps.position.PositionResponseTRC;
import org.cognitivefinder.tracking.websocket.PositionWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PositionService {

    private final GPSPositionService gpsPositionService;
    private final PositionWebSocketHandler positionWebSocketHandler;

    public PositionService(GPSPositionService gpsPositionService, PositionWebSocketHandler positionWebSocketHandler) {
        this.gpsPositionService = gpsPositionService;
        this.positionWebSocketHandler = positionWebSocketHandler;
    }

    @Scheduled(fixedRate = 10000) // Every 10 seconds
    public void sendPositionToClient() {
        List<Long> deviceIds = positionWebSocketHandler.getDeviceGpsIds();
        if (deviceIds == null || deviceIds.isEmpty()) {
            log.warn("No device GPS IDs available. Skipping position update.");
            return;
        }

        List<PositionResponseTRC> positions = new ArrayList<>();
        for (Long deviceId : deviceIds) {
            try {
                PositionResponseTRC position = gpsPositionService.getPositionByDeviceId(deviceId);
                if (position != null) {
                    positions.add(position);
                }
            } catch (Exception e) {
                log.error("Error fetching position for device ID {}: {}", deviceId, e.getMessage(), e);
            }
        }

        if (!positions.isEmpty()) {
            positionWebSocketHandler.sendPositionToClient(positions);
            log.info("Sent {} positions to WebSocket client.", positions.size());
        } else {
            log.warn("No positions available to send.");
        }
    }
}

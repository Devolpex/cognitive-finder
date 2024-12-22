package org.cognitivefinder.tracking.modules.track;

import org.cognitivefinder.tracking.gps.position.GPSPositionService;
import org.cognitivefinder.tracking.gps.position.PositionResponseTRC;
import org.cognitivefinder.tracking.websocket.PositionWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class PositionService {

    @Autowired
    private GPSPositionService gpsPositionService;

    private final PositionWebSocketHandler positionWebSocketHandler;

    public PositionService(PositionWebSocketHandler positionWebSocketHandler) {
        this.positionWebSocketHandler = positionWebSocketHandler;
    }

    // Send request to Traccar every 10 seconds
    @Scheduled(fixedRate = 10000) // 10 seconds
    public void sendPositionToClient() {

        try {
            PositionResponseTRC position = gpsPositionService.getPositionByDeviceId(1L);
            positionWebSocketHandler.sendPositionToClient(position);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

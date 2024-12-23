package org.cognitivefinder.tracking.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cognitivefinder.tracking.gps.position.PositionResponseTRC;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

@Component
public class PositionWebSocketHandler extends TextWebSocketHandler {

    private WebSocketSession session;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
        System.out.println("WebSocket connection established");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // Handle any incoming messages if needed
    }

    public void sendPositionToClient(PositionResponseTRC position) {
        if (session != null && session.isOpen()) {
            try {
                // Convert the PositionResponseTRC object to JSON using ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonMessage = objectMapper.writeValueAsString(position);

                // Send the JSON message to the client
                session.sendMessage(new TextMessage(jsonMessage));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package org.cognitivefinder.tracking.websocket;

import org.cognitivefinder.tracking.hello.HelloWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final HelloWebSocketHandler helloWebSocketHandler;
    private final PositionWebSocketHandler positionWebSocketHandler;

    public WebSocketConfig(HelloWebSocketHandler helloWebSocketHandler,
            PositionWebSocketHandler positionWebSocketHandler) {
        this.helloWebSocketHandler = helloWebSocketHandler;
        this.positionWebSocketHandler = positionWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(helloWebSocketHandler, "/ws/hello")
                .addHandler(positionWebSocketHandler, "/ws/position")
                .setAllowedOrigins("*");
    }
}

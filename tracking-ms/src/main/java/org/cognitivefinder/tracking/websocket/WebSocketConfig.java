package org.cognitivefinder.tracking.websocket;

import org.cognitivefinder.tracking.hello.HelloWebSocketHandler;
import org.cognitivefinder.tracking.security.KeycloakAuthHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final HelloWebSocketHandler helloWebSocketHandler;
    private final PositionWebSocketHandler positionWebSocketHandler;
    private final JwtDecoder jwtDecoder;

    public WebSocketConfig(HelloWebSocketHandler helloWebSocketHandler,
            PositionWebSocketHandler positionWebSocketHandler,
            JwtDecoder jwtDecoder) {
        this.helloWebSocketHandler = helloWebSocketHandler;
        this.positionWebSocketHandler = positionWebSocketHandler;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(helloWebSocketHandler, "/ws/hello")
                .addHandler(positionWebSocketHandler, "/ws/position")
                .setAllowedOrigins("http://localhost:4200")
                .addInterceptors(new KeycloakAuthHandshakeInterceptor(jwtDecoder));
    }
}

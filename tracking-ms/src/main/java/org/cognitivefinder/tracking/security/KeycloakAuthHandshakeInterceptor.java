package org.cognitivefinder.tracking.security;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.http.HttpHeaders;

import java.util.Map;

public class KeycloakAuthHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtDecoder jwtDecoder;

    // Constructor to inject JwtDecoder
    public KeycloakAuthHandshakeInterceptor(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    // Method to decode the OAuth2 token and extract the user ID (sub)
    private String extractUserIdFromToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token); // Decode the token
            return jwt.getClaimAsString("sub"); // Extract the 'sub' claim (user ID)
        } catch (Exception e) {
            return null; // Return null if there's any issue with decoding
        }
    }

    @Override
    public boolean beforeHandshake(org.springframework.http.server.ServerHttpRequest request,
                                     org.springframework.http.server.ServerHttpResponse response,
                                     WebSocketHandler wsHandler,
                                     Map<String, Object> attributes) throws Exception {

        // Extract the OAuth2 token from the Authorization header
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extract token

            // Extract user ID ('sub') from the token
            String userId = extractUserIdFromToken(token);
            if (userId != null) {
                // If the token is valid and user ID is extracted, proceed with the WebSocket connection
                attributes.put("userId", userId); // Store userId in WebSocket session attributes
                attributes.put("token", token); // Store token in WebSocket session attributes
                return true;
            } else {
                response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                return false; // Reject connection if token is invalid or sub is missing
            }
        }

        // Reject WebSocket handshake if Authorization header is missing or invalid
        response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterHandshake(org.springframework.http.server.ServerHttpRequest request,
                                org.springframework.http.server.ServerHttpResponse response,
                                WebSocketHandler wsHandler,
                                Exception exception) {
        // Handle after handshake logic if necessary (no changes needed here)
    }
}

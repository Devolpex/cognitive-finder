package org.cognitivefinder.tracking.gps.session;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GpsSessionService {

    @Value("${traccar.api}")
    private String BASE_URL;

    @Value("${traccar.user}")
    private String username;
    @Value("${traccar.password}")
    private String password;

    private String cookie;
    private final RestTemplate restTemplate;

    public synchronized String getSession() {
        if (cookie == null) {
            cookie = this.createSession(username, password);
        }
        return cookie;
    }

    private String createSession(String email, String password) {
        String sessionEndpoint = BASE_URL + "/session/";

        // Create the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        // Create the request body
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("email", email);
        formData.add("password", password);

        // Create the request entity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

        // Send the request
        ResponseEntity<String> response = restTemplate.exchange(sessionEndpoint, HttpMethod.POST, requestEntity,
                String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Session created successfully");

            // Extract the Set-Cookie header
            String setCookieHeader = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
            if (setCookieHeader != null) {
                // Parse the cookies and extract the JSESSIONID
                for (String cookie : setCookieHeader.split(";")) {
                    if (cookie.trim().startsWith("JSESSIONID=")) {
                        String sessionId = cookie.split("=")[1];
                        return sessionId; // Return the session ID
                    }
                }
            }
        } else {
            log.error("Failed to create session: " + response.getStatusCode());
        }

        return null;
    }

    // Get the session cookie to use later
    public synchronized String getSessionCookieForWebSocket() {
        return cookie;
    }

    // Method that will run once when the application starts
    @PostConstruct
    public void init() {
        log.info("Initializing GPS session service");
        // Get the session cookie when the application starts
        this.getSession();
    }

    // Scheduled task to refresh the session every 12 hours
    @Scheduled(fixedRate = 12, timeUnit = TimeUnit.HOURS)
    public synchronized void refreshSession() {
        log.info("Refreshing GPS session in Time:" + System.currentTimeMillis());
        this.getSession(); // Fetch a new session cookie every 12 hours
    }

}

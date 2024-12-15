package org.cognitivefinder.microservice.traccar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class TraccarHeaders {

    @Value("${traccar.user}")
    private static String username;
    @Value("${traccar.password}")
    private static String password;

    // Helper method to create HTTP headers with basic auth
    public static HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}

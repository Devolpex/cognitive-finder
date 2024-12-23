package org.cognitivefinder.tracking.gps.position;

import org.cognitivefinder.tracking.errors.exception.BusinessException;
import org.cognitivefinder.tracking.gps.TraccarHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.core.ParameterizedTypeReference;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GPSPositionService {

    @Value("${traccar.api}")
    private String BASE_URL;

    private final RestTemplate restTemplate;
    private final TraccarHeaders traccarHeaders;

    // Get the first position by deviceId from Traccar
    public PositionResponseTRC getPositionByDeviceId(Long deviceId) {
        String url = BASE_URL + "/positions?deviceId=" + deviceId;

        HttpHeaders headers = traccarHeaders.createHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            // Use ParameterizedTypeReference to handle the generic type
            ResponseEntity<List<PositionResponseTRC>> response = restTemplate.exchange(
                    url, HttpMethod.GET, request, new ParameterizedTypeReference<List<PositionResponseTRC>>() {}
            );

            // Check if the response is valid and return the first object in the list
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && !response.getBody().isEmpty()) {
                log.info("Successfully fetched positions for deviceId: " + deviceId);
                return response.getBody().get(0); // Return the first position object
            } else {
                log.warn("No positions found for deviceId: " + deviceId);
                throw new BusinessException("No positions found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error fetching positions for deviceId: " + deviceId, e);
            throw new BusinessException("Error fetching positions", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

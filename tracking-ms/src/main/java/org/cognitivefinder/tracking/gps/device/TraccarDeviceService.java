package org.cognitivefinder.tracking.gps.device;

import java.util.Map;

import org.cognitivefinder.tracking.errors.exception.BusinessException;
import org.cognitivefinder.tracking.gps.TraccarHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TraccarDeviceService {

    @Value("${traccar.api}")
    private String BASE_URL;

    private final RestTemplate restTemplate;

    private final TraccarHeaders traccarHeaders;

    // Add device to Traccar
    public Long addDevice(DeviceTCR body) {
        String url = BASE_URL + "/devices";
        HttpHeaders headers = traccarHeaders.createHeaders();
        HttpEntity<DeviceTCR> request = new HttpEntity<>(body, headers);

        // Send the POST request
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);
            // Cast the response to a map and get the id
            if (response == null) {
                log.error("Error to save device in traccar", response);
                throw new BusinessException("Error save the device in Traccar system",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Long traccarId = Long.parseLong(response.get("id").toString());
            log.info("Device created in Traccar with id: " + traccarId);
            return traccarId;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
            throw new BusinessException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update device in Traccar
    public void updateDevice(DeviceTCR body, Long traccarId) {
        String url = BASE_URL + "/devices/" + traccarId;
        HttpHeaders headers = traccarHeaders.createHeaders();
        HttpEntity<DeviceTCR> request = new HttpEntity<>(body, headers);
        try {
            restTemplate.exchange(url, HttpMethod.PUT, request, Map.class);
            log.info("Device updated in Traccar with id: " + traccarId);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete device in Traccar
    public void deleteDevice(Long traccarId) {
        String url = BASE_URL + "/devices/" + traccarId;
        HttpHeaders headers = traccarHeaders.createHeaders();
        HttpEntity<DeviceTCR> request = new HttpEntity<>(headers);
        try {
            restTemplate.exchange(url, HttpMethod.DELETE, request, Map.class);
            log.info("Device deleted in Traccar with id: " + traccarId);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

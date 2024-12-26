package org.cognitivefinder.tracking.modules.localisation;

import java.util.List;

import org.cognitivefinder.tracking.gps.position.PositionResponseTRC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LocalisationREST {

    private final LocationService locationService;

    @GetMapping("/api/v1/positions/client/{clientId}")
    public List<PositionResponseTRC> getPositionsByClientId(@PathVariable("clientId") String clientId) {
        return locationService.getPositionsByClientId(clientId);
    }
    
}

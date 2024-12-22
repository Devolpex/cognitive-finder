package org.cognitivefinder.tracking.modules.track;

import org.cognitivefinder.tracking.gps.position.GPSPositionService;
import org.cognitivefinder.tracking.gps.position.PositionResponseTRC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TrackController {

    private final GPSPositionService gpsPositionService;

    @GetMapping("/track/{deviceId}")
    public ResponseEntity<PositionResponseTRC> getTrack(@PathVariable Long deviceId) {
        return ResponseEntity.ok(gpsPositionService.getPositionByDeviceId(deviceId));
    }

}

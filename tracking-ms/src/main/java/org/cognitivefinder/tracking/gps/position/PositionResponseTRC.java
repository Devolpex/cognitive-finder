package org.cognitivefinder.tracking.gps.position;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionResponseTRC {

    private long id;
    private PositionAttributes attributes;
    private long deviceId;
    private String protocol;
    private String serverTime;
    private String deviceTime;
    private String fixTime;
    private boolean outdated;
    private boolean valid;
    private double latitude;
    private double longitude;
    private double altitude;
    private double speed;
    private double course;
    private String address;
    private double accuracy;
    private String network;
    private List<Long> geofenceIds;

    // Inner class to represent the "attributes" object
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PositionAttributes {
        private double batteryLevel;
        private double distance;
        private double totalDistance;
        private boolean motion;
    }
}

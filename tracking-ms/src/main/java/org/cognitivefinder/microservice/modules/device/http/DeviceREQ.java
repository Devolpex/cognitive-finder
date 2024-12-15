package org.cognitivefinder.microservice.modules.device.http;

import lombok.Builder;

@Builder
public record DeviceREQ(
    String imei,
    String sim,
    String patientId
) {
    
}

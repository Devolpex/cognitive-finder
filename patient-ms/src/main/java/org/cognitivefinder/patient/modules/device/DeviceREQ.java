package org.cognitivefinder.patient.modules.device;

import lombok.Builder;

@Builder
public record DeviceREQ(
    String imei,
    String sim,
    String patientId
) {
    
}

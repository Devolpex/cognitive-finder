package org.cognitivefinder.patient.modules.patient.http;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PatientREQ(
    @NotBlank(message = "Name of patient is required")
    String name,
    String maladie,
    // @NotBlank(message = "Client ID is required")
    // String clientId,
    @NotBlank(message = "Device IMEI is required")
    String deviceImei,
    @NotBlank(message = "Device number is required")
    String deviceNumber
) {
}
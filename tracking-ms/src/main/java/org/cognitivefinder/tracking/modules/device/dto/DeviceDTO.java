package org.cognitivefinder.tracking.modules.device.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceDTO {
    private Long id;
    private String imei;
    private String sim;
    private String patientId;
    private Long traccarId;
}

package org.cognitivefinder.patient.modules.patient.dto;

import org.cognitivefinder.patient.modules.device.DeviceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PatientDTO {
    private String id;
    private String name;
    private String maladie;
    private ClientDTO client;
    private DeviceDTO device;
}

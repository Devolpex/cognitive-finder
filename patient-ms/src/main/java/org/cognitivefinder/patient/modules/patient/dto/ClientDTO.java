package org.cognitivefinder.patient.modules.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClientDTO {
    private Long id;
    private String name;
    private String email;
}

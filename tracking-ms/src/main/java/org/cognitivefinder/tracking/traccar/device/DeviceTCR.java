package org.cognitivefinder.tracking.traccar.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceTCR {
    private String uniqueId;
    private String name;
    private String phone;
}

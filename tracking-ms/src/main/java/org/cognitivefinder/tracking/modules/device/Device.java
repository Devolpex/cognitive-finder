package org.cognitivefinder.tracking.modules.device;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "devices")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String imei;
    @Column(unique = true)
    private String sim;
    @Column(unique = true)
    private String patientId;
    @Column(unique = true)
    private Long traccarId;
    
}

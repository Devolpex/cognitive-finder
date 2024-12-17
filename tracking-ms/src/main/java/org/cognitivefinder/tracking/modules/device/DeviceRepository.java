package org.cognitivefinder.tracking.modules.device;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Long> {

    Optional<Device> findByPatientId(String patientId);

    Optional<Device> findByPatientIdIn(List<String> patientIds);
    
}

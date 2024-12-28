package org.cognitivefinder.tracking.modules.device;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    
    @Query("SELECT d FROM Device d WHERE d.patientId = :patientId")
    Optional<Device> findByPatientId(String patientId);

    List<Device> findByPatientIdIn(List<String> patientIds);

}

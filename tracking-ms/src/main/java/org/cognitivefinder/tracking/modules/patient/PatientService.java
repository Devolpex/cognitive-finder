package org.cognitivefinder.tracking.modules.patient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "patient-microservice")
public interface PatientService {

    @GetMapping("/api/v1/patients-ids/client/{clientId}")
    List<String> fetchPatientIdsByClientId(@PathVariable("clientId") String clientId);

}

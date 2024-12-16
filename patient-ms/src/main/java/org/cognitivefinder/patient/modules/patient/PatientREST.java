package org.cognitivefinder.patient.modules.patient;

import java.util.List;

import org.cognitivefinder.patient.modules.patient.dto.PatientDTO;
import org.cognitivefinder.patient.modules.patient.http.PatientREQ;
import org.cognitivefinder.patient.utils.IRESTController;
import org.cognitivefinder.patient.utils.OwnPageRES;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PatientREST implements IRESTController<PatientDTO, PatientREQ, PatientREQ, String> {
    private final PatientServiceImpl patientService;

    @Override
    public ResponseEntity<PatientDTO> fetchById(String id) {
        return ResponseEntity.ok(patientService.findById(id));
    }

    @Override
    @GetMapping("/api/v1/patients")
    public ResponseEntity<List<PatientDTO>> fetchAll() {
        return ResponseEntity.ok(patientService.findAll());
    }

    @Override
    @PostMapping("/api/v1/patient")
    public ResponseEntity<PatientDTO> create(@RequestBody PatientREQ req) {
        return ResponseEntity.ok(patientService.create(req));
    }

    @Override
    public ResponseEntity<PatientDTO> update(PatientREQ req, String id) {
        return ResponseEntity.ok(patientService.update(id, req));
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        patientService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<OwnPageRES<PatientDTO>> fetchAll(Integer page, Integer size, String sortBy, String orderBy,
            String search, List<String> filter) {
                return null;
    }

}

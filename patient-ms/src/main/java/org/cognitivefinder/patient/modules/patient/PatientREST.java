package org.cognitivefinder.patient.modules.patient;

import java.util.List;

import org.cognitivefinder.patient.modules.patient.dto.PatientDTO;
import org.cognitivefinder.patient.modules.patient.http.PatientREQ;
import org.cognitivefinder.patient.utils.IRESTController;
import org.cognitivefinder.patient.utils.OwnPageRES;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PatientREST implements IRESTController<PatientDTO, PatientREQ, PatientREQ, String> {
    private final PatientServiceImpl patientService;

    @Override
    @GetMapping("/api/v1/patient/{id}")
    public ResponseEntity<PatientDTO> fetchById(@PathVariable String id) {
        return ResponseEntity.ok(patientService.findById(id));
    }

    @Override
    @GetMapping("/api/v1/patients/list")
    public ResponseEntity<List<PatientDTO>> fetchAll() {
        return ResponseEntity.ok(patientService.findAll());
    }

    @Override
    @PostMapping("/api/v1/patient")
    public ResponseEntity<PatientDTO> create(@RequestBody @Valid PatientREQ req) {
        return ResponseEntity.ok(patientService.create(req));
    }

    @Override
    @PutMapping("/api/v1/patient/{id}")
    public ResponseEntity<PatientDTO> update(@RequestBody @Valid PatientREQ req, @PathVariable String id) {
        return ResponseEntity.ok(patientService.update(id, req));
    }

    @Override
    @DeleteMapping("/api/v1/patient/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        patientService.delete(id);
        return ResponseEntity.ok().build();
    }

    // TODO: Implement the fetchAll method
    @Override
    @GetMapping("/api/v1/patients")
    public ResponseEntity<OwnPageRES<PatientDTO>> fetchAll(Integer page, Integer size, String sortBy, String orderBy,
            String search, List<String> filter) {
                return null;
    }

}

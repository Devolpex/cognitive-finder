package org.cognitivefinder.patient.patient;

import org.cognitivefinder.patient.modules.patient.Patient;
import org.cognitivefinder.patient.modules.patient.PatientMapperImpl;
import org.cognitivefinder.patient.modules.patient.dto.PatientDTO;
import org.cognitivefinder.patient.modules.patient.http.PatientREQ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PatientMapperImplTest {

    private PatientMapperImpl patientMapper;

    @BeforeEach
    void setUp() {
        patientMapper = new PatientMapperImpl();
    }

    @Test
    void testToDTO() {
        // Given
        Patient patient = new Patient();
        patient.setId("patientId");
        patient.setName("John Doe");
        patient.setMaladie("Flu");

        // When
        PatientDTO patientDTO = patientMapper.toDTO(patient);

        // Then
        assertNotNull(patientDTO);
        assertEquals(patient.getId(), patientDTO.getId());
        assertEquals(patient.getName(), patientDTO.getName());
        assertEquals(patient.getMaladie(), patientDTO.getMaladie());
    }

    @Test
    void testToEntityCreate() {
        // Given
        PatientREQ patientREQ = PatientREQ.builder()
            .name("John Doe")
            .maladie("Flu")
            .build();

        // When
        Patient patient = patientMapper.toEntity(patientREQ);

        // Then
        assertNotNull(patient);
        assertEquals(patientREQ.name(), patient.getName());
        assertEquals(patientREQ.maladie(), patient.getMaladie());
    }

    @Test
    void testToEntityUpdate() {
        // Given
        Patient patient = new Patient();
        patient.setId("patientId");
        patient.setName("Old Name");
        patient.setMaladie("Old Maladie");

        PatientREQ patientREQ = PatientREQ.builder()
            .name("New Name")
            .maladie("New Maladie")
            .build();

        // When
        Patient updatedPatient = patientMapper.toEntity(patientREQ, patient);

        // Then
        assertNotNull(updatedPatient);
        assertEquals(patientREQ.name(), updatedPatient.getName());
        assertEquals(patientREQ.maladie(), updatedPatient.getMaladie());
    }
}
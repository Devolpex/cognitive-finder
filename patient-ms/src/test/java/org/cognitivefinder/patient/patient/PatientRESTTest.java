package org.cognitivefinder.patient.patient;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.cognitivefinder.patient.modules.patient.PatientREST;
import org.cognitivefinder.patient.modules.patient.PatientServiceImpl;
import org.cognitivefinder.patient.modules.patient.dto.PatientDTO;
import org.cognitivefinder.patient.modules.patient.http.PatientREQ;
import org.cognitivefinder.patient.utils.OwnPageRES;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class PatientRESTTest {

    @Mock
    private PatientServiceImpl patientService;

    @InjectMocks
    private PatientREST patientREST;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchById() {
        String patientId = "123";
        PatientDTO mockPatient = PatientDTO.builder().id(patientId).name("John Doe").build();
        when(patientService.findById(patientId)).thenReturn(mockPatient);

        ResponseEntity<PatientDTO> response = patientREST.fetchById(patientId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockPatient, response.getBody());
        verify(patientService, times(1)).findById(patientId);
    }

    @Test
    void testFetchAll() {
        List<PatientDTO> mockPatients = List.of(
            PatientDTO.builder().id("123").name("John Doe").build(),
            PatientDTO.builder().id("456").name("Jane Doe").build()
        );
        when(patientService.findAll()).thenReturn(mockPatients);

        ResponseEntity<List<PatientDTO>> response = patientREST.fetchAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockPatients, response.getBody());
        verify(patientService, times(1)).findAll();
    }

    @Test
    void testCreate() {
        PatientREQ request = PatientREQ.builder().name("John Doe").build();
        PatientDTO mockPatient = PatientDTO.builder().id("123").name("John Doe").build();
        when(patientService.create(request)).thenReturn(mockPatient);

        ResponseEntity<PatientDTO> response = patientREST.create(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockPatient, response.getBody());
        verify(patientService, times(1)).create(request);
    }

    @Test
    void testUpdate() {
        String patientId = "123";
        PatientREQ request = PatientREQ.builder().name("John Doe Updated").build();
        PatientDTO mockUpdatedPatient = PatientDTO.builder().id(patientId).name("John Doe Updated").build();
        when(patientService.update(patientId, request)).thenReturn(mockUpdatedPatient);

        ResponseEntity<PatientDTO> response = patientREST.update(request, patientId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockUpdatedPatient, response.getBody());
        verify(patientService, times(1)).update(patientId, request);
    }

    @Test
    void testDelete() {
        String patientId = "123";

        ResponseEntity<Void> response = patientREST.delete(patientId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(patientService, times(1)).delete(patientId);
    }

    @Test
    void testFetchPatientIdsByClientId() {
        String clientId = "client1";
        List<String> mockIds = List.of("123", "456");
        when(patientService.findAllByClientId(clientId)).thenReturn(mockIds);

        ResponseEntity<List<String>> response = patientREST.fetchPatientIdsByClientId(clientId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockIds, response.getBody());
        verify(patientService, times(1)).findAllByClientId(clientId);
    }

    @Test
    void testFetchPatientsByClientId() {
        String clientId = "client1";
        List<PatientDTO> mockPatients = List.of(
               PatientDTO.builder().id("123").name("John Doe").build(),
                PatientDTO.builder().id("456").name("Jane Doe").build()
        );
        when(patientService.fetchByClientId(clientId)).thenReturn(mockPatients);

        ResponseEntity<List<PatientDTO>> response = patientREST.fetchPatientsByClientId(clientId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockPatients, response.getBody());
        verify(patientService, times(1)).fetchByClientId(clientId);
    }
}

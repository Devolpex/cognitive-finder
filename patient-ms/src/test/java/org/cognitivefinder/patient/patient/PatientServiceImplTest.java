package org.cognitivefinder.patient.patient;

import org.cognitivefinder.patient.errors.exception.BusinessException;
import org.cognitivefinder.patient.modules.device.DeviceDTO;
import org.cognitivefinder.patient.modules.device.DeviceREQ;
import org.cognitivefinder.patient.modules.device.DeviceService;
import org.cognitivefinder.patient.modules.patient.Patient;
import org.cognitivefinder.patient.modules.patient.PatientMapperImpl;
import org.cognitivefinder.patient.modules.patient.PatientRepository;
import org.cognitivefinder.patient.modules.patient.PatientServiceImpl;
import org.cognitivefinder.patient.modules.patient.dto.PatientDTO;
import org.cognitivefinder.patient.modules.patient.http.PatientREQ;
import org.cognitivefinder.patient.utils.OwnPageRES;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PatientServiceImplTest {

    @InjectMocks
    private PatientServiceImpl patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapperImpl patientMapper;

    @Mock
    private DeviceService deviceService;

    private Patient patient;
    private PatientDTO patientDTO;
    private DeviceDTO deviceDTO;
    private PatientREQ patientREQ;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup patient and request data
        patient = Patient.builder().id("1").name("John Doe").maladie("Flu").build();
        patientDTO = PatientDTO.builder().id("1").name("John Doe").maladie("Flu").build();
        deviceDTO = DeviceDTO.builder().imei("imei123").sim("sim123").build();
        patientREQ = PatientREQ.builder().name("John Doe").maladie("Flu").deviceImei("imei123").deviceNumber("sim123")
                .build();

        // Mock the device service to return a DeviceDTO
        when(deviceService.fetchByPatientId(anyString())).thenReturn(deviceDTO);
    }

    // @Test
    // void testCreate() {
    // // Arrange
    // when(patientRepository.save(any(Patient.class))).thenReturn(patient);
    // when(patientMapper.toDTO(any(Patient.class))).thenReturn(patientDTO);
    // when(deviceService.createDevice(any(DeviceREQ.class))).thenReturn(deviceDTO);

    // // Act
    // PatientDTO result = patientService.create(patientREQ);

    // // Assert
    // assertNotNull(result);
    // assertEquals("1", result.getId());
    // assertEquals("John Doe", result.getName());
    // assertEquals("Flu", result.getMaladie());
    // assertNotNull(result.getDevice());
    // assertEquals("imei123", result.getDevice().getImei());
    // }

    // @Test
    // void testUpdate() {
    // // Arrange
    // // Creating mock Patient and PatientDTO
    // Patient patient = Patient.builder().id("1").name("John
    // Doe").maladie("Cold").build();
    // PatientDTO patientDTO = PatientDTO.builder().id("1").name("John
    // Doe").maladie("Cold").build();

    // // Creating mock DeviceDTO
    // DeviceDTO deviceDTO = new DeviceDTO();
    // deviceDTO.setImei("imei123");
    // deviceDTO.setSim("sim123");

    // // Defining the update request
    // PatientREQ updateRequest = PatientREQ.builder().name("Jane
    // Doe").maladie("Cold").deviceImei("imei456")
    // .deviceNumber("sim456").build();

    // // Mocking the repository and other dependencies
    // when(patientRepository.findById(anyString())).thenReturn(Optional.of(patient));
    // when(patientMapper.toDTO(any(Patient.class))).thenReturn(patientDTO);
    // when(deviceService.fetchByPatientId(anyString())).thenReturn(deviceDTO);
    // when(deviceService.update(anyLong(),
    // any(DeviceREQ.class))).thenReturn(deviceDTO);

    // // Act
    // PatientDTO result = patientService.update("1", updateRequest);

    // // Assert
    // assertNotNull(result, "PatientDTO should not be null");
    // assertEquals("Jane Doe", result.getName(), "Patient name should be updated");
    // assertEquals("Cold", result.getMaladie(), "Patient disease should remain the
    // same");
    // assertNotNull(result.getDevice(), "Device should not be null");
    // assertEquals("imei456", result.getDevice().getImei(), "Device IMEI should
    // match the updated value");
    // }

    @Test
    void testFindById() {
        // Arrange
        when(patientRepository.findById(anyString())).thenReturn(Optional.of(patient));
        when(patientMapper.toDTO(any(Patient.class))).thenReturn(patientDTO);
        when(deviceService.fetchByPatientId(anyString())).thenReturn(deviceDTO);

        // Act
        PatientDTO result = patientService.findById("1");

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("Flu", result.getMaladie());
        assertNotNull(result.getDevice());
        assertEquals("imei123", result.getDevice().getImei());
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        when(patientRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> patientService.findById("1"));
        assertEquals("Patient not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testDelete() {
        // Arrange
        when(patientRepository.findById(anyString())).thenReturn(Optional.of(patient));
        doNothing().when(deviceService).deleteByPatientId(anyString());
        doNothing().when(patientRepository).deleteById(anyString());

        // Act
        patientService.delete("1");

        // Assert
        verify(patientRepository, times(1)).deleteById(anyString());
        verify(deviceService, times(1)).deleteByPatientId(anyString());
    }

    @Test
    void testDeleteNotFound() {
        // Arrange
        when(patientRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> patientService.delete("1"));
        assertEquals("Patient not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testFindAll() {
        // Arrange
        // Mocking the deviceDTO and its behavior
        DeviceDTO deviceDTO = mock(DeviceDTO.class);
        when(deviceDTO.getImei()).thenReturn("imei123");

        // Mocking the patientDTO and ensuring it returns the mocked deviceDTO
        PatientDTO patientDTO = mock(PatientDTO.class);
        when(patientDTO.getDevice()).thenReturn(deviceDTO);
        when(patientDTO.getName()).thenReturn("John Doe");

        // Mocking the patientRepository and patientMapper
        List<Patient> patients = List.of(patient); // Ensure 'patient' is properly initialized
        when(patientRepository.findAll()).thenReturn(patients);
        when(patientMapper.toDTO(any(Patient.class))).thenReturn(patientDTO);

        // Mocking the deviceService to return the deviceDTO
        when(deviceService.fetchByPatientId(anyString())).thenReturn(deviceDTO);

        // Act
        List<PatientDTO> result = patientService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("imei123", result.get(0).getDevice().getImei());
    }

    @Test
    void testFindAllEmptyList() {
        // Arrange
        when(patientRepository.findAll()).thenReturn(List.of());

        // Act
        List<PatientDTO> result = patientService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

}

package org.cognitivefinder.tracking.device;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.cognitivefinder.tracking.errors.exception.BusinessException;
import org.cognitivefinder.tracking.modules.device.*;
import org.cognitivefinder.tracking.modules.device.dto.DeviceDTO;
import org.cognitivefinder.tracking.modules.device.http.DeviceREQ;
import org.cognitivefinder.tracking.traccar.device.DeviceTCR;
import org.cognitivefinder.tracking.traccar.device.TraccarDeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

public class DeviceServiceImplTest {

    @InjectMocks
    private DeviceServiceImpl service;

    @Mock
    private DeviceRepository repository;

    @Mock
    private DeviceMapperImpl mapper;

    @Mock
    private TraccarDeviceService traccarDeviceService;

    private Device device;
    private DeviceDTO deviceDTO;
    private DeviceREQ request;
    private DeviceTCR deviceTCR;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = DeviceREQ.builder().imei("123456789").sim("123456789").patientId("PatientID-123").build();
        device = Device.builder().id(1L).imei(request.imei()).sim(request.sim()).patientId(request.patientId())
                .traccarId(1L).build();
        deviceDTO = DeviceDTO.builder().id(1L).imei(request.imei()).sim(request.sim())
                .patientId(request.patientId()).traccarId(1L).build();
        deviceTCR = DeviceTCR.builder().name(request.patientId()).uniqueId(request.imei()).phone(request.sim()).build();
    }

    @Test
    void createDeviceTest() {
        // Mock
        when(mapper.toEntity(request)).thenReturn(device);
        when(mapper.toDTO(device)).thenReturn(deviceDTO);
        when(traccarDeviceService.addDevice(deviceTCR)).thenReturn(1L);
        when(repository.save(device)).thenReturn(device);

        // Call
        DeviceDTO response = service.create(request);

        // Assertions
        assertDeviceDTO(response, deviceDTO);
        verify(repository, times(1)).save(device);
        verify(traccarDeviceService, times(1)).addDevice(deviceTCR);
    }

    @Test
    void updateDeviceTest() {
        // Mock
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(device));
        when(mapper.toEntity(request, device)).thenReturn(device);
        when(mapper.toDTO(device)).thenReturn(deviceDTO);
        when(repository.save(device)).thenReturn(device);

        // Call
        DeviceDTO response = service.update(id, request);

        // Assertions
        assertDeviceDTO(response, deviceDTO);
        verify(repository, times(1)).save(device);
        verify(traccarDeviceService, times(1)).updateDevice(deviceTCR, device.getTraccarId());

        // Error Case
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertBusinessException(() -> service.update(id, request), "Device not found");
    }

    @Test
    void findDeviceByIdTest() {
        Long id = 1L;

        // Mock
        when(repository.findById(id)).thenReturn(Optional.of(device));
        when(mapper.toDTO(device)).thenReturn(deviceDTO);

        // Call
        DeviceDTO response = service.findById(id);

        // Assertions
        assertDeviceDTO(response, deviceDTO);

        // Error Case
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertBusinessException(() -> service.findById(id), "Device not found");
    }

    @Test
    void deleteDeviceTest() {
        Long id = 1L;

        // Mock
        when(repository.findById(id)).thenReturn(Optional.of(device));

        // Call
        service.delete(id);

        // Verify
        verify(repository, times(1)).delete(device);

        // Error Case
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertBusinessException(() -> service.delete(id), "Device not found");
    }

    @Test
    void findLidtOfDevicesTest() {
        // Mock
        when(repository.findAll()).thenReturn(List.of(device));
        when(mapper.toDTO(device)).thenReturn(deviceDTO);

        // Call
        List<DeviceDTO> response = service.findAll();

        // Assertions
        assertNotNull(response);
        assertEquals(1, response.size());
        assertDeviceDTO(response.get(0), deviceDTO);
    }

    @Test
    void findDeviceByPatientIdTest() {
        String patientId = "PatientID-123";

        // Mock
        when(repository.findByPatientId(patientId)).thenReturn(Optional.of(device));
        when(mapper.toDTO(device)).thenReturn(deviceDTO);

        // Call
        DeviceDTO response = service.findByPatientId(patientId);

        // Assertions
        assertDeviceDTO(response, deviceDTO);
    }

    @Test
    void deleteDeviceByPatientIdTest() {
        String patientId = "PatientID-123";

        // Mock
        when(repository.findByPatientId(patientId)).thenReturn(Optional.of(device));

        // Call
        service.deleteByPatientId(patientId);

        // Verify
        verify(repository, times(1)).delete(device);

        // Error Case
        when(repository.findByPatientId(patientId)).thenReturn(Optional.empty());
        assertBusinessException(() -> service.deleteByPatientId(patientId), "Device not found");
    }

    private void assertDeviceDTO(DeviceDTO actual, DeviceDTO expected) {
        assertNotNull(actual);
        assertEquals(expected, actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getImei(), actual.getImei());
        assertEquals(expected.getSim(), actual.getSim());
        assertEquals(expected.getPatientId(), actual.getPatientId());
        assertEquals(expected.getTraccarId(), actual.getTraccarId());
    }

    private void assertBusinessException(Executable executable, String expectedMessage) {
        BusinessException exception = assertThrows(BusinessException.class, executable);
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}

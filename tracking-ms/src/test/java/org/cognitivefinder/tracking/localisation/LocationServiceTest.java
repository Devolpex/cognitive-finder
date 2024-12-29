package org.cognitivefinder.tracking.localisation;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.cognitivefinder.tracking.errors.exception.BusinessException;
import org.cognitivefinder.tracking.gps.position.GPSPositionService;
import org.cognitivefinder.tracking.gps.position.PositionResponseTRC;
import org.cognitivefinder.tracking.modules.device.Device;
import org.cognitivefinder.tracking.modules.device.DeviceRepository;
import org.cognitivefinder.tracking.modules.localisation.LocationService;
import org.cognitivefinder.tracking.modules.patient.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

public class LocationServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private GPSPositionService gpsPositionService;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPositionsByClientId() {
        String clientId = "client123";
        List<String> patientIds = Arrays.asList("patient1", "patient2");
        List<Device> devices = Arrays.asList(new Device(), new Device());
        devices.get(0).setId(1L);
        devices.get(1).setId(2L);
        PositionResponseTRC position1 = new PositionResponseTRC();
        PositionResponseTRC position2 = new PositionResponseTRC();

        when(patientService.fetchPatientIdsByClientId(clientId)).thenReturn(patientIds);
        when(deviceRepository.findByPatientIdIn(patientIds)).thenReturn(devices);
        when(gpsPositionService.getPositionByDeviceId(1L)).thenReturn(position1);
        when(gpsPositionService.getPositionByDeviceId(2L)).thenReturn(position2);

        List<PositionResponseTRC> positions = locationService.getPositionsByClientId(clientId);

        assertNotNull(positions);
        assertEquals(2, positions.size());
        assertFalse(positions.contains(position1));
        assertFalse(positions.contains(position2));

        verify(patientService, times(1)).fetchPatientIdsByClientId(clientId);
        verify(deviceRepository, times(1)).findByPatientIdIn(patientIds);
        // verify(gpsPositionService, times(1)).getPositionByDeviceId(1L);
        // verify(gpsPositionService, times(1)).getPositionByDeviceId(2L);
    }

    @Test
    void testGetPositionByPatientId() {
        String patientId = "patient123";
        Device device = new Device(1L, "000000000", "0000000000", "patient123", 1L);
        PositionResponseTRC position = new PositionResponseTRC(1L, new PositionResponseTRC.PositionAttributes(50.0, 100.0, 150.0, true), 123L, "protocol1", "2023-01-01T00:00:00Z", "2023-01-01T00:00:00Z", "2023-01-01T00:00:00Z", false, true, 12.34, 56.78, 9.0, 10.0, 180.0, "address1", 5.0, "network1", Arrays.asList(1L, 2L));

        when(deviceRepository.findByPatientId(patientId)).thenReturn(Optional.of(device));
        when(locationService.getPositionByPatientId(patientId)).thenReturn(position);
        when(gpsPositionService.getPositionByDeviceId(1L)).thenReturn(position);

        PositionResponseTRC result = locationService.getPositionByPatientId(patientId);

        assertNotNull(result);
        assertEquals(position, result);

        verify(deviceRepository, times(2)).findByPatientId(patientId);
        verify(gpsPositionService, times(1)).getPositionByDeviceId(1L);
    }

    @Test
    void testGetPositionByPatientId_PatientNotFound() {
        String patientId = "patient123";

        when(deviceRepository.findByPatientId(patientId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            locationService.getPositionByPatientId(patientId);
        });

        assertEquals("Patient not exists", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

        verify(deviceRepository, times(1)).findByPatientId(patientId);
        verify(gpsPositionService, never()).getPositionByDeviceId(anyLong());
    }
}

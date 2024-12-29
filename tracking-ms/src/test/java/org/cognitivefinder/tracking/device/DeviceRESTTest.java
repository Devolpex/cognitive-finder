package org.cognitivefinder.tracking.device;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.cognitivefinder.tracking.modules.device.DeviceREST;
import org.cognitivefinder.tracking.modules.device.DeviceServiceImpl;
import org.cognitivefinder.tracking.modules.device.dto.DeviceDTO;
import org.cognitivefinder.tracking.modules.device.http.DeviceREQ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class DeviceRESTTest {

    @Mock
    private DeviceServiceImpl service;

    @InjectMocks
    private DeviceREST deviceREST;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(deviceREST).build();
    }

    @Test
    void testFetchById() {
        Long id = 1L;
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(id);
        
        when(service.findById(id)).thenReturn(deviceDTO);

        ResponseEntity<DeviceDTO> response = deviceREST.fetchById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
        verify(service, times(1)).findById(id);
    }

    @Test
    void testFetchAll() {
        DeviceDTO device1 = new DeviceDTO();
        DeviceDTO device2 = new DeviceDTO();
        List<DeviceDTO> devices = Arrays.asList(device1, device2);
        
        when(service.findAll()).thenReturn(devices);

        ResponseEntity<List<DeviceDTO>> response = deviceREST.fetchAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service, times(1)).findAll();
    }

    @Test
    void testCreate() {
        DeviceREQ request = DeviceREQ.builder().build();
        DeviceDTO deviceDTO = new DeviceDTO();
        
        when(service.create(any(DeviceREQ.class))).thenReturn(deviceDTO);

        ResponseEntity<DeviceDTO> response = deviceREST.create(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service, times(1)).create(any(DeviceREQ.class));
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        DeviceREQ request = DeviceREQ.builder().build();
        DeviceDTO deviceDTO = new DeviceDTO();
        
        when(service.update(eq(id), any(DeviceREQ.class))).thenReturn(deviceDTO);

        ResponseEntity<DeviceDTO> response = deviceREST.update(request, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service, times(1)).update(eq(id), any(DeviceREQ.class));
    }

    @Test
    void testDelete() {
        Long id = 1L;
        
        doNothing().when(service).delete(id);

        ResponseEntity<Void> response = deviceREST.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(id);
    }

    @Test
    void testFetchByPatientId() {
        String patientId = "12345";
        DeviceDTO deviceDTO = new DeviceDTO();
        
        when(service.findByPatientId(patientId)).thenReturn(deviceDTO);

        ResponseEntity<DeviceDTO> response = deviceREST.fetchByPatientId(patientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service, times(1)).findByPatientId(patientId);
    }

    @Test
    void testDeleteByPatientId() {
        String patientId = "12345";
        
        doNothing().when(service).deleteByPatientId(patientId);

        ResponseEntity<Void> response = deviceREST.deleteByPatientId(patientId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deleteByPatientId(patientId);
    }

    @Test
    void testFetchByPatientIds() {
        List<String> patientIds = Arrays.asList("12345", "67890");
        DeviceDTO device1 = new DeviceDTO();
        DeviceDTO device2 = new DeviceDTO();
        List<DeviceDTO> devices = Arrays.asList(device1, device2);
        
        when(service.findByPatientIds(patientIds)).thenReturn(devices);

        ResponseEntity<List<DeviceDTO>> response = deviceREST.fetchByPatientIds(patientIds);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service, times(1)).findByPatientIds(patientIds);
    }
}

package org.cognitivefinder.tracking.localisation;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.cognitivefinder.tracking.gps.position.PositionResponseTRC;
import org.cognitivefinder.tracking.modules.localisation.LocalisationREST;
import org.cognitivefinder.tracking.modules.localisation.LocationService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;


@ExtendWith(MockitoExtension.class)
class LocalisationRESTTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocalisationREST localisationREST;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(localisationREST).build();
    }
    @Test
    void testGetPositionsByClientId() throws Exception {
        // Given
        String clientId = "client123";
        PositionResponseTRC position1 = new PositionResponseTRC();
        PositionResponseTRC position2 = new PositionResponseTRC();
        List<PositionResponseTRC> positions = Arrays.asList(position1, position2);
    
        // Mock the service method
        when(locationService.getPositionsByClientId(clientId)).thenReturn(positions);
    
        // When: Perform the actual HTTP request using MockMvc
        mockMvc.perform(get("/api/v1/positions/client/{clientId}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));  // Ensure the response contains two positions
    
        // Then: Verify that the service method was called exactly once
        verify(locationService, times(1)).getPositionsByClientId(clientId);
    }
    

    @Test
    void testGetPositionsByPatientId() {
        // Given
        String patientId = "patient123";
        PositionResponseTRC position = new PositionResponseTRC();

        when(locationService.getPositionByPatientId(patientId)).thenReturn(position);

        // When
        ResponseEntity<PositionResponseTRC> response = localisationREST.getPositionsByPatientId(patientId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(locationService, times(1)).getPositionByPatientId(patientId);
    }
}

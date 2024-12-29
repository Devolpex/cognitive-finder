package org.cognitivefinder.tracking;
import static org.junit.jupiter.api.Assertions.*;
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





public class TrackingApplicationTestsTest {

  @InjectMocks
  private LocationService locationService;

  @Mock
  private DeviceRepository deviceRepository;

  @Mock
  private GPSPositionService gpsPositionService;

  @Mock
  private PatientService patientService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetPositionsByClientId() {
    String clientId = "client123";
    List<String> patientIds = Arrays.asList("patient1", "patient2");
    List<Device> devices = Arrays.asList(
        Device.builder().traccarId(1L).build(),
        Device.builder().traccarId(2L).build()
    );
    PositionResponseTRC position1 = new PositionResponseTRC();
    PositionResponseTRC position2 = new PositionResponseTRC();

    when(patientService.fetchPatientIdsByClientId(clientId)).thenReturn(patientIds);
    when(deviceRepository.findByPatientIdIn(patientIds)).thenReturn(devices);
    when(gpsPositionService.getPositionByDeviceId(1L)).thenReturn(position1);
    when(gpsPositionService.getPositionByDeviceId(2L)).thenReturn(position2);

    List<PositionResponseTRC> positions = locationService.getPositionsByClientId(clientId);

    assertNotNull(positions);
    assertEquals(2, positions.size());
    assertTrue(positions.contains(position1));
    assertTrue(positions.contains(position2));
  }

  @Test
  void testGetPositionByPatientId() {
    String patientId = "patient123";
    Device device = Device.builder().traccarId(1L).build();
    PositionResponseTRC position = new PositionResponseTRC();

    when(deviceRepository.findByPatientId(patientId)).thenReturn(Optional.of(device));
    when(gpsPositionService.getPositionByDeviceId(1L)).thenReturn(position);

    PositionResponseTRC result = locationService.getPositionByPatientId(patientId);

    assertNotNull(result);
    assertEquals(position, result);
  }

  @Test
  void testGetPositionByPatientId_NotFound() {
    String patientId = "patient123";

    when(deviceRepository.findByPatientId(patientId)).thenReturn(Optional.empty());

    BusinessException exception = assertThrows(BusinessException.class, () -> {
      locationService.getPositionByPatientId(patientId);
    });

    assertEquals("Patient not exists", exception.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
  }
}
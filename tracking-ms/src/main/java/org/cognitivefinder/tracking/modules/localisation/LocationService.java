package org.cognitivefinder.tracking.modules.localisation;

import java.util.ArrayList;
import java.util.List;

import org.cognitivefinder.tracking.errors.exception.BusinessException;
import org.cognitivefinder.tracking.gps.position.GPSPositionService;
import org.cognitivefinder.tracking.gps.position.PositionResponseTRC;
import org.cognitivefinder.tracking.modules.device.Device;
import org.cognitivefinder.tracking.modules.device.DeviceRepository;
import org.cognitivefinder.tracking.modules.patient.PatientService;
import org.springframework.http.HttpStatus;
// import org.cognitivefinder.tracking.security.AuthService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    private final DeviceRepository deviceRepository;
    private final GPSPositionService gpsPositionService;
    private final PatientService patientService;

    public List<PositionResponseTRC> getPositionsByClientId(String clientId) {
        // Get the patient id by the client id
        List<String> patientIds = patientService.fetchPatientIdsByClientId(clientId);
        // Get the device id by the patient id
        List<Device> devices = deviceRepository.findByPatientIdIn(patientIds);
        // Get the positions by the device id
        List<PositionResponseTRC> positions = new ArrayList<>();
        for (Device device : devices) {
            PositionResponseTRC position = gpsPositionService.getPositionByDeviceId(device.getTraccarId());
            positions.add(position);
        }
        return positions;
    }

    public PositionResponseTRC getPositionByPatientId(String patientId) {
        Device device = deviceRepository.findByPatientId(patientId)
                .orElseThrow(() -> {
                    log.info("Patient with id " + patientId + "not exists");
                    return new BusinessException("Patient not exists", HttpStatus.NOT_FOUND);
                });
        return gpsPositionService.getPositionByDeviceId(device.getTraccarId());

    }

}

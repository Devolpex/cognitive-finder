package org.cognitivefinder.patient.modules.patient;

import java.util.List;

import org.cognitivefinder.patient.errors.exception.BusinessException;
import org.cognitivefinder.patient.modules.device.DeviceDTO;
import org.cognitivefinder.patient.modules.device.DeviceREQ;
import org.cognitivefinder.patient.modules.device.DeviceService;
import org.cognitivefinder.patient.modules.patient.dto.PatientDTO;
import org.cognitivefinder.patient.modules.patient.http.PatientREQ;
import org.cognitivefinder.patient.utils.IService;
import org.cognitivefinder.patient.utils.OwnPageRES;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements IService<PatientDTO, PatientREQ, PatientREQ, String> {

    private final PatientRepository repository;
    private final PatientMapperImpl mapper;
    private final DeviceService deviceService;

    @Override
    public PatientDTO create(PatientREQ req) {
        // TODO: Check if the client exists

        Patient patient = Patient.builder()
                .name(req.name())
                .maladie(req.maladie())
                .build();
        patient = repository.save(patient);

        // TODO: Send the device information to the tracking microservice
        DeviceREQ deviceREQ = DeviceREQ.builder()
                .patientId(patient.getId())
                .imei(req.deviceImei())
                .sim(req.deviceNumber())
                .build();
        DeviceDTO deviceDTO = deviceService.createDevice(deviceREQ);
        PatientDTO patientDTO = mapper.toDTO(patient);
        patientDTO.setDevice(deviceDTO);

        return patientDTO;
    }

    @Override
    public PatientDTO update(String id, PatientREQ req) {
        // TODO: Check if the client exists

        return repository.findById(id)
                .map(p -> {
                    p.setName(req.name());
                    p.setMaladie(req.maladie());
                    p = repository.save(p);

                    /**
                     * TODO: Get the device information form the tracking microservice, if has
                     * changed update the device information on Tracking microservice
                     */
                    return mapper.toDTO(p);
                })
                .orElseThrow(() -> {
                    log.error("Patient not found");
                    return new BusinessException("Patient not found", HttpStatus.NOT_FOUND);
                });
    }

    @Override
    public PatientDTO findById(String id) {
        return repository.findById(id)
                .map((patient) -> {
                    // TODO: Get the device information form the tracking microservice
                    return mapper.toDTO(patient);
                })
                .orElseThrow(() -> {
                    log.error("Patient not found");
                    return new BusinessException("Patient not found", HttpStatus.NOT_FOUND);
                });
    }

    @Override
    public void delete(String id) {
        repository.findById(id)
                .ifPresentOrElse(
                        patient -> {
                            // TODO: Delete the device information from the tracking microservice
                            repository.deleteById(id);
                            log.info("Patient with id {} deleted", id);
                        },
                        () -> {
                            log.error("Patient with id {} not found", id);
                            throw new BusinessException("Patient not found", HttpStatus.NOT_FOUND);
                        });
    }

    @Override
    public OwnPageRES<PatientDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<PatientDTO> findAll() {
        return null;
    }
}
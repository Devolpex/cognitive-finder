package org.cognitivefinder.tracking.modules.device;

import java.util.List;
import java.util.stream.Collectors;

import org.cognitivefinder.tracking.errors.exception.BusinessException;
import org.cognitivefinder.tracking.modules.device.dto.DeviceDTO;
import org.cognitivefinder.tracking.modules.device.http.DeviceREQ;
import org.cognitivefinder.tracking.traccar.device.DeviceTCR;
import org.cognitivefinder.tracking.traccar.device.TraccarDeviceService;
import org.cognitivefinder.tracking.utils.IService;
import org.cognitivefinder.tracking.utils.OwnPageRES;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceServiceImpl implements IService<DeviceDTO, DeviceREQ, DeviceREQ, Long> {

    private final DeviceRepository repository;
    private final DeviceMapperImpl mapper;
    private final TraccarDeviceService traccarDeviceService;

    @Override
    public DeviceDTO create(DeviceREQ req) throws BusinessException {
        // TODO: check if the patient already exists
        // TODO: check if the device is already associated with the patient

        // Add the device to the traccar
        DeviceTCR deviceTCR = DeviceTCR.builder()
                .name(req.patientId())
                .uniqueId(req.imei())
                .phone(req.sim())
                .build();
        Long traccarId = traccarDeviceService.addDevice(deviceTCR);
        Device device = mapper.toEntity(req);

        // Save the device in the database
        device.setTraccarId(traccarId);
        device = repository.save(device);
        return mapper.toDTO(device);
    }

    @Override
    public DeviceDTO update(Long id, DeviceREQ req) throws BusinessException {
        // TODO: check if the device exists
        // TODO: check if the device is already associated with the patient

        return repository.findById(id)
                .map((device) -> {
                    DeviceTCR deviceTCR = DeviceTCR.builder()
                            .name(req.patientId())
                            .uniqueId(req.imei())
                            .phone(req.sim())
                            .build();
                    traccarDeviceService.updateDevice(deviceTCR, device.getTraccarId());
                    device = mapper.toEntity(req, device);
                    device = repository.save(device);
                    return mapper.toDTO(device);
                })
                .orElseThrow(() -> {
                    log.error("Device not found with id {}", id);
                    return new BusinessException("Device not found", HttpStatus.NOT_FOUND);
                });
    }

    @Override
    public DeviceDTO findById(Long id) throws BusinessException {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> {
                    log.error("Device not found with id {}", id);
                    return new BusinessException("Device not found", HttpStatus.NOT_FOUND);
                });
    }

    @Override
    public void delete(Long id) throws BusinessException {
        repository.findById(id)
                .ifPresentOrElse(device -> {
                    repository.delete(device);
                }, () -> {
                    log.error("Device not found with id {}", id);
                    throw new BusinessException("Device not found", HttpStatus.NOT_FOUND);
                });
    }

    @Override
    public List<DeviceDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public OwnPageRES<DeviceDTO> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }


    // Service to get Device information by patient id
    public DeviceDTO findByPatientId(String patientId) {
        return repository.findByPatientId(patientId)
                .map(mapper::toDTO)
                .orElseThrow(() -> {
                    log.error("Device not found with patient id {}", patientId);
                    return new BusinessException("Device not found", HttpStatus.NOT_FOUND);
                });
    }

    // Service to delete device by patient id
    public void deleteByPatientId(String patientId) {
        repository.findByPatientId(patientId)
                .ifPresentOrElse(device -> {
                    repository.delete(device);
                }, () -> {
                    log.error("Device not found with patient id {}", patientId);
                    throw new BusinessException("Device not found", HttpStatus.NOT_FOUND);
                });
    }
}

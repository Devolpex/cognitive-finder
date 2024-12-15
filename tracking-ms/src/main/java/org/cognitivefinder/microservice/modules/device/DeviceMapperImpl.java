package org.cognitivefinder.microservice.modules.device;

import org.cognitivefinder.microservice.modules.device.dto.DeviceDTO;
import org.cognitivefinder.microservice.modules.device.http.DeviceREQ;
import org.cognitivefinder.microservice.utils.IMapper;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapperImpl implements IMapper<Device, DeviceDTO, DeviceREQ, DeviceREQ> {

    @Override
    public DeviceDTO toDTO(Device entity) {
        if (entity == null) {
            return null;
        } else {
            return DeviceDTO.builder()
                    .id(entity.getId())
                    .imei(entity.getImei())
                    .sim(entity.getSim())
                    .patientId(entity.getPatientId())
                    .traccarId(entity.getTraccarId())
                    .build();
        }
    }

    @Override
    public Device toEntity(DeviceREQ createRequest) {
        return Device.builder()
                .imei(createRequest.imei())
                .sim(createRequest.sim())
                .patientId(createRequest.patientId())
                .build();
    }

    @Override
    public Device toEntity(DeviceREQ updateRequest, Device entity) {
        entity.setImei(updateRequest.imei());
        entity.setSim(updateRequest.sim());
        entity.setPatientId(updateRequest.patientId());
        return entity;
    }

}

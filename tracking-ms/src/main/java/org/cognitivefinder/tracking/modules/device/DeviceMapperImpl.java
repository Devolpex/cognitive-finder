package org.cognitivefinder.tracking.modules.device;

import org.cognitivefinder.tracking.modules.device.dto.DeviceDTO;
import org.cognitivefinder.tracking.modules.device.http.DeviceREQ;
import org.cognitivefinder.tracking.utils.IMapper;
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
        if (createRequest == null) {
            return null;
        }
        return Device.builder()
                .imei(createRequest.imei())
                .sim(createRequest.sim())
                .patientId(createRequest.patientId())
                .build();
    }

    @Override
    public Device toEntity(DeviceREQ updateRequest, Device entity) {
        if (updateRequest == null) {
            return null;
        }
        if (entity == null) {
            return null;
        }
        entity.setImei(updateRequest.imei());
        entity.setSim(updateRequest.sim());
        entity.setPatientId(updateRequest.patientId());
        return entity;
    }

}

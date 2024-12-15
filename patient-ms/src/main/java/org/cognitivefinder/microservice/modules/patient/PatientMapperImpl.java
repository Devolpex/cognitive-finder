package org.cognitivefinder.microservice.modules.patient;

import org.cognitivefinder.microservice.modules.patient.dto.PatientDTO;
import org.cognitivefinder.microservice.modules.patient.http.PatientREQ;
import org.cognitivefinder.microservice.utils.IMapper;
import org.springframework.stereotype.Component;

@Component
public class PatientMapperImpl implements IMapper<Patient,PatientDTO,PatientREQ,PatientREQ> {

    @Override
    public PatientDTO toDTO(Patient entity) {
        return PatientDTO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .maladie(entity.getMaladie())
            .build();
    }

    @Override
    public Patient toEntity(PatientREQ createRequest) {
        return Patient.builder()
            .name(createRequest.name())
            .maladie(createRequest.maladie())
            .build();
    }

    @Override
    public Patient toEntity(PatientREQ updateRequest, Patient entity) {
        entity.setName(updateRequest.name());
        entity.setMaladie(updateRequest.maladie());
        return entity;
    }
    
}

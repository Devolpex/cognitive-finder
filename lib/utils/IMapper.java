package org.cognitivefinder.microservice.utils;

public interface IMapper<E,DTO,CREQ,UREQ> {
    DTO toDTO(E entity);
    E toEntity(CREQ createRequest);
    E toEntity(UREQ updateRequest, E entity);
}
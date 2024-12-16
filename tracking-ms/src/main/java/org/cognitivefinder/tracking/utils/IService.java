package org.cognitivefinder.tracking.utils;

import java.util.List;

import org.cognitivefinder.tracking.errors.exception.BusinessException;
import org.springframework.data.domain.Pageable;

public interface IService<DTO, CREQ, UREQ, ID> {
    DTO create(CREQ req) throws BusinessException;

    DTO update(ID id, UREQ req) throws BusinessException;

    DTO findById(ID id) throws BusinessException;

    void delete(ID id) throws BusinessException;

    List<DTO> findAll();

    OwnPageRES<DTO> findAll(Pageable pageable);

}
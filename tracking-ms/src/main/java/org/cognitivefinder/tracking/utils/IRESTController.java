package org.cognitivefinder.tracking.utils;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface IRESTController<DTO, CREQ, UREQ, ID> {

    ResponseEntity<DTO> fetchById(ID id);

    ResponseEntity<List<DTO>> fetchAll();

    ResponseEntity<DTO> create(CREQ req);

    ResponseEntity<DTO> update(UREQ req, ID id);

    ResponseEntity<Void> delete(ID id);

    ResponseEntity<OwnPageRES<DTO>> fetchAll(
            Integer page,
            Integer size,
            String sortBy,
            String orderBy,
            String search,
            List<String> filter);

}

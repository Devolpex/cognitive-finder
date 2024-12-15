package org.cognitivefinder.microservice.modules.device;

import java.util.List;

import org.cognitivefinder.microservice.modules.device.dto.DeviceDTO;
import org.cognitivefinder.microservice.modules.device.http.DeviceREQ;
import org.cognitivefinder.microservice.utils.IRESTController;
import org.cognitivefinder.microservice.utils.OwnPageRES;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DeviceREST implements IRESTController<DeviceDTO, DeviceREQ, DeviceREQ, Long> {

    private final DeviceServiceImpl service;

    @Override
    @GetMapping("/api/v1/device/{id}")
    public ResponseEntity<DeviceDTO> fetchById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @Override
    @GetMapping("/api/v1/devices/list")
    public ResponseEntity<List<DeviceDTO>> fetchAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @Override
    @PostMapping("/api/v1/device")
    public ResponseEntity<DeviceDTO> create(@RequestBody @Valid DeviceREQ req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @Override
    @PutMapping("/api/v1/device/{id}")
    public ResponseEntity<DeviceDTO> update(@RequestBody @Valid DeviceREQ req, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, req));
    }

    @Override
    @DeleteMapping("/api/v1/device/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<OwnPageRES<DeviceDTO>> fetchAll(Integer page, Integer size, String sortBy, String orderBy,
            String search, List<String> filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fetchAll'");
    }

}

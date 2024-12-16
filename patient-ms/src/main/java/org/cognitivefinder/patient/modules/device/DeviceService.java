package org.cognitivefinder.patient.modules.device;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "tracking-microservice")
public interface DeviceService {

    @PostMapping("/api/v1/device")
    DeviceDTO createDevice(@RequestBody DeviceREQ req);

    @GetMapping("/api/v1/device/{id}")
    DeviceDTO fetchById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/devices/list")
    List<DeviceDTO> fetchAll();

    @PutMapping("/api/v1/device/{id}")
    DeviceDTO update(@PathVariable("id") Long id, @RequestBody DeviceREQ req);

    @DeleteMapping("/api/v1/device/{id}")
    void delete(@PathVariable("id") Long id);
    
}
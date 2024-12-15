package org.cognitivefinder.microservice.modules.device;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "tracking-microservice")
public interface DeviceService {

    @PostMapping("/api/v1/device")
    DeviceDTO createDevice(@RequestBody DeviceREQ req);

    
}
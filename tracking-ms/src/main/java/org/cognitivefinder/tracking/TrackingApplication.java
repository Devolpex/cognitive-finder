package org.cognitivefinder.tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class TrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackingApplication.class, args);

	}

}

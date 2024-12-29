package org.cognitivefinder.apigateway.routes;

import java.net.URI;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class RoutesConfig {

    // private final String PATIENT_SERVICE = "lb://patient-microservice";
    // private final String TRACKING_SERVICE = "lb//tracking-microservice";
    private final String PATIENT_SERVICE = "http://localhost:8882";
    private final String TRACKING_SERVICE = "http://localhost:8883";

    @Bean
    public RouterFunction<ServerResponse> patientServiceRoute() {
        return GatewayRouterFunctions.route("patient_service")
                .route(RequestPredicates.path("/api/v1/patient/**")
                        .or(RequestPredicates.path("/api/v1/patients/**"))
                        .or(RequestPredicates.path("/api/v1/patient-ids/**")),
                        HandlerFunctions.http(PATIENT_SERVICE))
                // .filter(CircuitBreakerFilterFunctions.circuitBreaker("patientServiceCircuitBreaker",
                //         URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> trackingServiceRoute() {
        return GatewayRouterFunctions.route("tracking_service")
                .route(RequestPredicates.path("/api/v1/device/**")
                        .or(RequestPredicates.path("/api/v1/devices/**"))
                        .or(RequestPredicates.path("/api/v1/positions/**")),
                        HandlerFunctions.http(TRACKING_SERVICE))
                // .filter(CircuitBreakerFilterFunctions.circuitBreaker("trackingServiceCircuitBreaker",
                //         URI.create("forward:/fallbackRoute")))
                .build();
    }

}

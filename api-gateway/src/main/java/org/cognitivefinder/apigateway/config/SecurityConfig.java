// package org.cognitivefinder.microservice.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
// import org.springframework.security.config.web.server.ServerHttpSecurity;
// import org.springframework.security.web.server.SecurityWebFilterChain;
// import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
// import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

// @Configuration
// @EnableWebFluxSecurity
// public class SecurityConfig {

//     @Bean
//     public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//         http
//                 .authorizeExchange(exchanges -> exchanges
//                         .pathMatchers("/api/test/public").permitAll()
//                         .pathMatchers("/api/test/client").hasRole("app-client")
//                         .pathMatchers("/api/test/admin").hasRole("app-admin")
//                         .anyExchange().authenticated())
//                 .oauth2ResourceServer(oauth2 -> oauth2
//                         .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
//         return http.build();
//     }

//     private ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
//         ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
//         converter.setJwtGrantedAuthoritiesConverter(new JwtAuthConverter());
//         return converter;
//     }
// }

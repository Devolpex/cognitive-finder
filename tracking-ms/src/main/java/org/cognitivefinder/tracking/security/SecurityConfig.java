// package org.cognitivefinder.tracking.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.web.SecurityFilterChain;

// import lombok.RequiredArgsConstructor;

// @Configuration
// @EnableWebSecurity
// @RequiredArgsConstructor
// public class SecurityConfig {
//     private static final String CLIENT = "CLIENT";
//     private static final String ADMIN = "ADMIN";
//     private static final String[] PUBLIC_PATHS = {
//             "/auth/**",
//             "/v2/api-docs",
//             "/v3/api-docs",
//             "/v3/api-docs/**",
//             "/swagger-resources",
//             "/swagger-resources/**",
//             "/configuration/ui",
//             "/configuration/security",
//             "/swagger-ui/**",
//             "/webjars/**",
//             "/swagger-ui.html",
//             "/actuator/**",
//     };

//     @Bean
//     public SecurityFilterChain configuration(HttpSecurity http) throws Exception {
//         // http.cors(withDefaults());
//         http.csrf(AbstractHttpConfigurer::disable);

//         http.authorizeHttpRequests(req -> req.requestMatchers(PUBLIC_PATHS).permitAll()
//                 .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                 // .requestMatchers(HttpMethod.POST, "/api/v1/device").permitAll()
//                 // .requestMatchers(HttpMethod.POST, "/api/v1/patient").hasRole(CLIENT)
//                 .requestMatchers("/**").permitAll()

//                 .requestMatchers("/ws/**").authenticated()
//                 .anyRequest()
//                 .authenticated());

//         http.oauth2ResourceServer(
//                 auth -> auth.jwt(token -> token.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())));

//         return http.build();

//     }

// }

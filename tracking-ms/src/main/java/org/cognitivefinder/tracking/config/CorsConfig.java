// package org.cognitivefinder.tracking.config;


// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class CorsConfig implements WebMvcConfigurer {

//     @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         // Allow CORS for WebSocket connections
//         registry.addMapping("/**")
//                 .allowedOrigins("http://localhost:5") // Allow Angular frontend
//                 .allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE","PATCH") // Allow specific methods
//                 .allowedHeaders("Content-Type", "Authorization") // Allow specific headers
//                 .allowCredentials(true); // Allows cookies if needed
//     }
// }

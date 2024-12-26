// package org.cognitivefinder.tracking.security;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.oauth2.jwt.Jwt;
// import org.springframework.stereotype.Service;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class AuthService {

//     public String getAuthenticatedUserId() {
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//         if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
//             Jwt jwt = (Jwt) authentication.getPrincipal();
//             return jwt.getClaim("sub"); // Extract the "sub" claim as the user ID
//         }
//         throw new RuntimeException("Unable to extract user ID from the JWT");
//     }

// }

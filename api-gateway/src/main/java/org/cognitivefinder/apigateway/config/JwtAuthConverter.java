package org.cognitivefinder.apigateway.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtAuthConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        // Extract authorities from the JWT
        Collection<GrantedAuthority> authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
        // Extract roles from the 'resource_access' -> 'api-gateway' claim
        authorities.addAll(extractApiGatewayRoles(jwt));
        // Wrap the result in a Mono
        return Mono.just(new JwtAuthenticationToken(jwt, authorities, getPrincipleClaimName(jwt)));
    }

    private String getPrincipleClaimName(Jwt jwt) {
        // Default claim name
        return "sub";
    }

    private Collection<GrantedAuthority> extractApiGatewayRoles(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");

        if (resourceAccess == null || resourceAccess.get("api-gateway") == null) {
            return Set.of();
        }

        Map<String, Object> apiGateway = (Map<String, Object>) resourceAccess.get("api-gateway");
        Collection<String> roles = (Collection<String>) apiGateway.get("roles");

        if (roles == null) {
            return Set.of();
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}

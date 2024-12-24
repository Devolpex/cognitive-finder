package org.cognitivefinder.tracking.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        return new JwtAuthenticationToken(
                source,
                Stream.concat(
                        new JwtGrantedAuthoritiesConverter().convert(source).stream(),
                        extractResourceRoles(source).stream())
                        .collect(toSet()));
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        // Extract the realm_access claim
        var realmAccess = (Map<String, Object>) jwt.getClaim("realm_access");

        // Extract the roles array
        var roles = (List<String>) realmAccess.get("roles");

        // Filter and map roles to GrantedAuthority objects
        return roles.stream()
                .filter(role -> role.equals("CLIENT")) // Filter for CLIENT role
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.replace("-", "_")))
                .collect(Collectors.toSet());
    }

}

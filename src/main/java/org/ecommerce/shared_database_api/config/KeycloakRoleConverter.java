package org.ecommerce.shared_database_api.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        // Extract roles from "realm_access"
        Collection<GrantedAuthority> realmAccessRoles = extractRolesFromAccess(source, "realm_access");

        // Extract roles from "resource_access" (example for "eazypublicclient" - you might need to adjust this)
        Collection<GrantedAuthority> resourceAccessRoles = extractRolesFromResourceAccess(source, "ecommerceBackend");

        // Combine both roles
        realmAccessRoles.addAll(resourceAccessRoles);

        return realmAccessRoles;
    }

    private Collection<GrantedAuthority> extractRolesFromAccess(Jwt jwt, String accessType) {
        Map<String, Object> accessMap = (Map<String, Object>) jwt.getClaims().get(accessType);
        if (accessMap == null || accessMap.isEmpty()) {
            return List.of();
        }
        return ((List<String>) accessMap.get("roles")).stream()
                .map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Collection<GrantedAuthority> extractRolesFromResourceAccess(Jwt jwt, String resourceKey) {
        Map<String, Object> resourceAccess = (Map<String, Object>) jwt.getClaims().get("resource_access");
        if (resourceAccess == null || resourceAccess.isEmpty()) {
            return List.of();
        }
        Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get(resourceKey);
        if (clientAccess == null || clientAccess.isEmpty()) {
            return List.of();
        }
        return ((List<String>) clientAccess.get("roles")).stream()
                .map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

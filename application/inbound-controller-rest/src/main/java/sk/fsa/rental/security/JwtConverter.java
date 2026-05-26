package sk.fsa.rental.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import sk.fsa.rental.rest.dto.UserRoleDto;

import java.util.*;

class JwtConverter extends AbstractAuthenticationToken {

    private final Jwt source;

    JwtConverter(Jwt source) {
        super(toAuthorities(source));
        this.source = Objects.requireNonNull(source);
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return Collections.emptyList();
    }

    @Override
    public Object getPrincipal() {
        return new AuthenticatedUser(
                source.getClaimAsString("sub"),
                source.getClaimAsString("email"),
                source.getClaimAsString("given_name"),
                source.getClaimAsString("family_name"),
                getRole());
    }

    private UserRoleDto getRole() {
        return findRole(extractRoles(source)).orElse(null);
    }

    private Optional<UserRoleDto> findRole(List<String> roles) {
        return roles.stream()
                .filter(role -> Arrays.stream(UserRoleDto.values())
                        .anyMatch(enumRole -> enumRole.name().equals(role)))
                .map(UserRoleDto::fromValue)
                .findFirst();
    }

    private static Collection<? extends GrantedAuthority> toAuthorities(Jwt source) {
        return extractRoles(source).stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    private static List<String> extractRoles(Jwt source) {
        Map<String, Object> realmAccess = source.getClaimAsMap("realm_access");
        if (realmAccess == null || realmAccess.get("roles") == null) {
            return List.of();
        }

        Object roles = realmAccess.get("roles");
        if (roles instanceof List<?> roleList) {
            return roleList.stream()
                    .map(Object::toString)
                    .toList();
        }
        throw new BadJwtException("Invalid realm_access.roles claim");
    }
}

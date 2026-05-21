package sk.fsa.rental.security;

import sk.fsa.rental.rest.dto.UserRoleDto;

/**
 * Internal security principal — holds JWT claims extracted from the Keycloak token.
 * Not exposed via the REST API.
 */
public class AuthenticatedUser {

    private final String keycloakId;
    private final String email;
    private final String name;
    private final String surname;
    private final UserRoleDto role;

    public AuthenticatedUser(String keycloakId, String email, String name, String surname, UserRoleDto role) {
        this.keycloakId = keycloakId;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public String getKeycloakId() { return keycloakId; }

    public String getEmail() { return email; }

    public String getName() { return name; }

    public String getSurname() { return surname; }

    public UserRoleDto getRole() { return role; }
}

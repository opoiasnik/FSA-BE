package sk.fsa.rental.security;

import sk.fsa.rental.rest.dto.UserRoleDto;

/**
 * Internal security principal — holds JWT claims extracted from the Keycloak token.
 * Not exposed via the REST API.
 */
public class AuthenticatedUser {

    private String keycloakId;
    private String email;
    private String name;
    private String surname;
    private UserRoleDto role;

    public String getKeycloakId() { return keycloakId; }
    public void setKeycloakId(String keycloakId) { this.keycloakId = keycloakId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public UserRoleDto getRole() { return role; }
    public void setRole(UserRoleDto role) { this.role = role; }
}

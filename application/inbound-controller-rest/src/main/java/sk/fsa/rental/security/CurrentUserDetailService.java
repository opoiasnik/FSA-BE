package sk.fsa.rental.security;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.UserRole;
import sk.fsa.rental.domain.facade.UserFacade;
import sk.fsa.rental.rest.dto.UserRoleDto;

@Service
public class CurrentUserDetailService {

    private final UserFacade userFacade;

    public CurrentUserDetailService(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public AuthenticatedUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new RentalException(RentalException.Type.UNAUTHORIZED, "Authentication required.");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthenticatedUser authenticatedUser) {
            return authenticatedUser;
        }
        throw new RentalException(RentalException.Type.UNAUTHORIZED, "Authentication required.");
    }

    @Transactional
    public User getFullCurrentUser() {
        AuthenticatedUser auth = getAuthenticatedUser();
        UserRole role = auth.getRole() == UserRoleDto.OWNER ? UserRole.OWNER : UserRole.USER;
        return userFacade.findOrCreate(auth.getKeycloakId(), auth.getEmail(), auth.getName(), auth.getSurname(), role);
    }
}

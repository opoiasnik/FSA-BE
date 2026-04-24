package sk.fsa.rental.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.UserFacade;
import sk.fsa.rental.rest.dto.UserDto;

@Service
public class CurrentUserDetailService {

    private final UserFacade userFacade;

    public CurrentUserDetailService(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public UserDto getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDto userDto) {
            return userDto;
        }
        throw new RentalException(RentalException.Type.UNAUTHORIZED, "Authentication required.");
    }

    public String getUserEmail() {
        return getCurrentUser().getEmail();
    }

    public User getFullCurrentUser() {
        return userFacade.findByEmail(getUserEmail());
    }
}

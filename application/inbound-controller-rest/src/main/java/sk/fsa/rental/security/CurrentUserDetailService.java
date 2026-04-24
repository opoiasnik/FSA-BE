package sk.fsa.rental.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.UserFacade;

@Service
public class CurrentUserDetailService {

    private final UserFacade userFacade;

    public CurrentUserDetailService(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String email) {
            return email;
        }
        throw new RentalException(RentalException.Type.UNAUTHORIZED, "Authentication required.");
    }

    public User getFullCurrentUser() {
        return userFacade.findByEmail(getCurrentUserEmail());
    }
}

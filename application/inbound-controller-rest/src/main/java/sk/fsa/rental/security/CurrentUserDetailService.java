package sk.fsa.rental.security;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.UserRole;
import sk.fsa.rental.domain.facade.UserFacade;
import sk.fsa.rental.rest.dto.UserDto;
import sk.fsa.rental.rest.dto.UserRoleDto;

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

    @Transactional
    public User getFullCurrentUser() {
        UserDto dto = getCurrentUser();
        UserRole role = dto.getRole() == UserRoleDto.OWNER ? UserRole.OWNER : UserRole.USER;
        return userFacade.findOrCreate(dto.getEmail(), dto.getName(), role);
    }
}

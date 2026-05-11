package sk.fsa.rental.domain.facade;

import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.UserRole;

public interface UserFacade {

    User findByEmail(String email);

    User findOrCreate(String email, String name, UserRole role);
}

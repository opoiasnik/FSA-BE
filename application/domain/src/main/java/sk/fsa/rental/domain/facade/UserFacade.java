package sk.fsa.rental.domain.facade;

import sk.fsa.rental.domain.User;

public interface UserFacade {

    User findByEmail(String email);
}

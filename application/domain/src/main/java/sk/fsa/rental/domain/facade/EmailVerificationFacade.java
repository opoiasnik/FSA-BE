package sk.fsa.rental.domain.facade;

import sk.fsa.rental.domain.User;

public interface EmailVerificationFacade {

    void request(User user);

    User confirm(User user, String code);
}

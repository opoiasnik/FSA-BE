package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.UserFacade;
import sk.fsa.rental.domain.repository.UserRepository;

public class UserService implements UserFacade {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "User not found."));
    }
}

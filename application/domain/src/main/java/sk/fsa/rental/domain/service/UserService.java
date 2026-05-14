package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.Photo;
import sk.fsa.rental.domain.PhotoFactory;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.UserRole;
import sk.fsa.rental.domain.facade.UserFacade;
import sk.fsa.rental.domain.repository.PhotoRepository;
import sk.fsa.rental.domain.repository.UserRepository;

public class UserService implements UserFacade {

    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final PhotoFactory photoFactory;

    public UserService(UserRepository userRepository, PhotoRepository photoRepository, PhotoFactory photoFactory) {
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.photoFactory = photoFactory;
    }

    @Override
    public User findOrCreate(String keycloakId, String email, String name, String surname, UserRole role) {
        return userRepository.findByKeycloakId(keycloakId)
                .orElseGet(() -> {
                    User user = new User(name, surname, email, role);
                    user.assignKeycloakId(keycloakId);
                    return userRepository.save(user);
                });
    }

    @Override
    public User updateProfile(User user, String name, String surname, String email, String phone, String bio) {
        user.updateProfile(name, surname, email, phone, bio);
        return userRepository.save(user);
    }

    @Override
    public User updateAvatar(User user, byte[] data, String contentType, String originalFilename) {
        Photo photo = photoFactory.create(data, contentType, originalFilename, null, null);
        Photo saved = photoRepository.save(photo);
        user.updateAvatar(saved);
        return userRepository.save(user);
    }
}

package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.Photo;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.UserRole;
import sk.fsa.rental.domain.facade.UserFacade;
import sk.fsa.rental.domain.repository.PhotoRepository;
import sk.fsa.rental.domain.repository.UserRepository;

public class UserService implements UserFacade {

    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;

    public UserService(UserRepository userRepository, PhotoRepository photoRepository) {
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
    }

    @Override
    public User findOrCreate(String keycloakId, String email, String name, String surname, UserRole role) {
        return userRepository.findByKeycloakId(keycloakId)
                .orElseGet(() -> {
                    User user = new User(name, surname, email, role);
                    user.setKeycloakId(keycloakId);
                    return userRepository.save(user);
                });
    }

    @Override
    public User updateProfile(User user, String name, String surname, String email, String phone, String bio) {
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setBio(bio);
        return userRepository.save(user);
    }

    @Override
    public User updateAvatar(User user, byte[] data, String contentType, String originalFilename) {
        if (data == null || data.length == 0) {
            throw new RentalException(RentalException.Type.VALIDATION, "Avatar file is required.", "file");
        }
        Photo photo = new Photo(data, contentType, originalFilename, null, null);
        Photo saved = photoRepository.save(photo);
        user.setAvatarPhoto(saved);
        return userRepository.save(user);
    }
}

package sk.fsa.rental.domain.facade;

import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.UserRole;

public interface UserFacade {

    User findOrCreate(String keycloakId, String email, String name, String surname, UserRole role);

    User updateProfile(User user, String name, String surname, String email, String phone, String bio);

    User updateAvatar(User user, byte[] data, String contentType, String originalFilename);
}

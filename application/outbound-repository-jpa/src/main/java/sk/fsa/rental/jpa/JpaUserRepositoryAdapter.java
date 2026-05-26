package sk.fsa.rental.jpa;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.repository.UserRepository;

import java.util.Optional;

@Repository
public class JpaUserRepositoryAdapter implements UserRepository {

    private final UserSpringDataRepository userSpringDataRepository;

    public JpaUserRepositoryAdapter(UserSpringDataRepository userSpringDataRepository) {
        this.userSpringDataRepository = userSpringDataRepository;
    }

    @Override
    public User save(User user) {
        try {
            return userSpringDataRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            if (user.getKeycloakId() == null || user.getKeycloakId().isBlank()) {
                throw ex;
            }
            return userSpringDataRepository.findByKeycloakId(user.getKeycloakId())
                    .orElseThrow(() -> ex);
        }
    }

    @Override
    public Optional<User> findByKeycloakId(String keycloakId) {
        return userSpringDataRepository.findByKeycloakId(keycloakId);
    }

}

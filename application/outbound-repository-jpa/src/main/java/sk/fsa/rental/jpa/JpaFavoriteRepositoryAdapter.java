package sk.fsa.rental.jpa;

import org.springframework.stereotype.Repository;
import sk.fsa.rental.domain.Favorite;
import sk.fsa.rental.domain.repository.FavoriteRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaFavoriteRepositoryAdapter implements FavoriteRepository {

    private final FavoriteSpringDataRepository favoriteSpringDataRepository;

    public JpaFavoriteRepositoryAdapter(FavoriteSpringDataRepository favoriteSpringDataRepository) {
        this.favoriteSpringDataRepository = favoriteSpringDataRepository;
    }

    @Override
    public Favorite save(Favorite favorite) {
        return favoriteSpringDataRepository.save(favorite);
    }

    @Override
    public Optional<Favorite> findByUserIdAndListingId(Long userId, Long listingId) {
        return favoriteSpringDataRepository.findByUserIdAndListingId(userId, listingId);
    }

    @Override
    public List<Favorite> findByUserId(Long userId) {
        return favoriteSpringDataRepository.findByUserId(userId);
    }

    @Override
    public void deleteById(Long id) {
        favoriteSpringDataRepository.deleteById(id);
    }
}

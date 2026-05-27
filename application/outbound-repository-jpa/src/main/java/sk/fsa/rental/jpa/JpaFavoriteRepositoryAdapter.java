package sk.fsa.rental.jpa;

import org.springframework.stereotype.Repository;
import sk.fsa.rental.domain.Favorite;
import sk.fsa.rental.domain.repository.FavoriteRepository;

import java.util.List;

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
    public List<Favorite> findByUserId(Long userId) {
        return favoriteSpringDataRepository.findByUserId(userId);
    }

    @Override
    public void deleteById(Long id) {
        favoriteSpringDataRepository.deleteById(id);
    }

    @Override
    public long countByListingOwnerId(Long ownerId) {
        return favoriteSpringDataRepository.countByListingOwnerId(ownerId);
    }
}

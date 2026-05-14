package sk.fsa.rental.domain.repository;

import sk.fsa.rental.domain.Favorite;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository {
    Favorite save(Favorite favorite);
    Optional<Favorite> findByUserIdAndListingId(Long userId, Long listingId);
    List<Favorite> findByUserId(Long userId);
    void deleteById(Long id);
    long countByListingOwnerId(Long ownerId);
}

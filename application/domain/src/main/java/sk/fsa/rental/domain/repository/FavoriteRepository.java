package sk.fsa.rental.domain.repository;

import sk.fsa.rental.domain.Favorite;

import java.util.List;

public interface FavoriteRepository {
    Favorite save(Favorite favorite);
    List<Favorite> findByUserId(Long userId);
    void deleteById(Long id);
    long countByListingOwnerId(Long ownerId);
}

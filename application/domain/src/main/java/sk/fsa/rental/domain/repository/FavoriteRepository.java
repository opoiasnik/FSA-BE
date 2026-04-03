package sk.fsa.rental.domain.repository;

import sk.fsa.rental.domain.Favorite;

import java.util.Collection;

public interface FavoriteRepository {
    void createFavorite(Favorite favorite);
    Collection<Favorite> findByUserId(Long userId);
    Collection<Favorite> readAll();
    void deleteById(Long id);
}

package sk.fsa.rental.domain.facade;

import sk.fsa.rental.domain.Favorite;
import sk.fsa.rental.domain.User;

import java.util.List;

public interface FavoriteFacade {
    Favorite addFavorite(Long listingId, User user);
    void removeFavorite(Long listingId, User user);
    List<Favorite> listFavoritesByUser(Long userId);
}

package sk.fsa.rental.domain.facade;

import sk.fsa.rental.domain.Favorite;
import sk.fsa.rental.domain.User;

import java.util.List;

public interface FavoriteFacade {
    Favorite add(Long listingId, User user);
    void remove(Long listingId, User user);
    List<Favorite> getByUser(Long userId);
}

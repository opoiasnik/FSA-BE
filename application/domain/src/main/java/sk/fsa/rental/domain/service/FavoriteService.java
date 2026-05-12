package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.Favorite;
import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.FavoriteFacade;
import sk.fsa.rental.domain.repository.FavoriteRepository;
import sk.fsa.rental.domain.repository.ListingRepository;

import java.util.List;

public class FavoriteService implements FavoriteFacade {

    private final FavoriteRepository favoriteRepository;
    private final ListingRepository listingRepository;

    public FavoriteService(FavoriteRepository favoriteRepository,
                           ListingRepository listingRepository) {
        this.favoriteRepository = favoriteRepository;
        this.listingRepository = listingRepository;
    }

    @Override
    public Favorite add(Long listingId, User user) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Listing not found."));
        Favorite favorite = user.addToFavorites(listing);
        return favoriteRepository.save(favorite);
    }

    @Override
    public void remove(Long listingId, User user) {
        Favorite removed = user.removeFromFavorites(listingId);
        favoriteRepository.deleteById(removed.getId());
    }

    @Override
    public List<Favorite> getByUser(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }
}

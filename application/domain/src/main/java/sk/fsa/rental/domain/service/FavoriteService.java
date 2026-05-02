package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.Favorite;
import sk.fsa.rental.domain.FavoriteFactory;
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
    private final FavoriteFactory favoriteFactory;

    public FavoriteService(FavoriteRepository favoriteRepository,
                           ListingRepository listingRepository,
                           FavoriteFactory favoriteFactory) {
        this.favoriteRepository = favoriteRepository;
        this.listingRepository = listingRepository;
        this.favoriteFactory = favoriteFactory;
    }

    @Override
    public Favorite addFavorite(Long listingId, User user) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Listing not found."));
        Favorite prepared = favoriteFactory.createFavorite(listing, user);
        return favoriteRepository.save(prepared);
    }

    @Override
    public void removeFavorite(Long listingId, User user) {
        Favorite existing = favoriteRepository.findByUserIdAndListingId(user.getId(), listingId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Favorite not found."));
        favoriteRepository.deleteById(existing.getId());
    }

    @Override
    public List<Favorite> listFavoritesByUser(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }
}

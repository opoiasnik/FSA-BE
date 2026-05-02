package sk.fsa.rental.domain;

import sk.fsa.rental.domain.repository.FavoriteRepository;

public class FavoriteFactory {

    private final FavoriteRepository favoriteRepository;

    public FavoriteFactory(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public Favorite createFavorite(Listing listing, User user) {
        require(listing != null,
                RentalException.Type.VALIDATION, "Listing must not be null.");
        require(user != null,
                RentalException.Type.VALIDATION, "User must not be null.");

        require(favoriteRepository.findByUserIdAndListingId(user.getId(), listing.getId()).isEmpty(),
                RentalException.Type.VALIDATION, "Listing is already in favorites.");

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setListing(listing);
        favorite.validateForCreation();
        return favorite;
    }

    private void require(boolean valid, RentalException.Type type, String message) {
        if (!valid) {
            throw new RentalException(type, message);
        }
    }
}

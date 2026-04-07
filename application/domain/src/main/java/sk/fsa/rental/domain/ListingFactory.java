package sk.fsa.rental.domain;

import sk.fsa.rental.domain.repository.ListingRepository;

public class ListingFactory {

    private final ListingRepository listingRepository;

    public ListingFactory(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public Listing createListing(Listing listing, User owner) {
        require(listing != null, "Listing must not be null.");
        require(owner != null, "Owner must not be null.");

        listing.setOwner(owner);
        listing.validateForCreation();

        boolean duplicate = listingRepository.existsByOwnerIdAndAddress(
                owner.getId(), listing.getAddress());
        if (duplicate) {
            throw new RentalException(RentalException.Type.VALIDATION,
                    "Owner already has a listing at this address.");
        }

        return listing;
    }

    private void require(boolean condition, String message) {
        if (!condition) {
            throw new RentalException(RentalException.Type.VALIDATION, message);
        }
    }
}

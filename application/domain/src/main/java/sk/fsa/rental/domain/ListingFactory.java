package sk.fsa.rental.domain;

import sk.fsa.rental.domain.repository.ListingRepository;

public class ListingFactory {

    private final ListingRepository listingRepository;

    public ListingFactory(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public Listing createListing(Listing listing, User owner) {
        if (listing == null) throw new RentalException(RentalException.Type.VALIDATION, "Listing must not be null.");
        if (owner == null) throw new RentalException(RentalException.Type.VALIDATION, "Owner must not be null.");

        listing.setOwner(owner);
        listing.validateForCreation();

        if (listingRepository.existsByOwnerIdAndAddress(owner.getId(), listing.getAddress())) {
            throw new RentalException(RentalException.Type.VALIDATION, "Owner already has a listing at this address.");
        }

        return listing;
    }
}

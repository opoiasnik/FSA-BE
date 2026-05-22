package sk.fsa.rental.domain;

import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.predicate.listing.RequiresGeocodingPredicate;
import sk.fsa.rental.domain.service.GeocodingService;

public class ListingFactory {

    private final ListingRepository listingRepository;
    private final GeocodingService geocodingService;

    public ListingFactory(ListingRepository listingRepository, GeocodingService geocodingService) {
        this.listingRepository = listingRepository;
        this.geocodingService = geocodingService;
    }

    public Listing create(Listing listing, User owner) {
        require(listing != null,
                RentalException.Type.VALIDATION, "Listing must not be null.");
        require(owner != null,
                RentalException.Type.VALIDATION, "Owner must not be null.");

        listing.setOwner(owner);
        listing.validateForCreation();

        require(!listingRepository.existsByOwnerIdAndAddress(owner.getId(), listing.getAddress()),
                RentalException.Type.VALIDATION, "Owner already has a listing at this address.");

        assignCoordinatesWhenNeeded(listing.getAddress());

        return listing;
    }

    public Listing update(Listing existing, Listing updatedListing, User editor) {
        require(existing != null,
                RentalException.Type.VALIDATION, "Existing listing must not be null.");
        require(updatedListing != null,
                RentalException.Type.VALIDATION, "Updated listing must not be null.");

        existing.update(
                editor,
                updatedListing.getTitle(),
                updatedListing.getDescription(),
                updatedListing.getListingType(),
                updatedListing.getAddress(),
                updatedListing.getPrice(),
                updatedListing.getFeatures()
        );
        assignCoordinatesWhenNeeded(existing.getAddress());
        return existing;
    }

    private void assignCoordinatesWhenNeeded(Address address) {
        if (!RequiresGeocodingPredicate.INSTANCE.test(address)) {
            return;
        }

        Coordinates coordinates = geocodingService.geocode(address)
                .orElseThrow(() -> new RentalException(
                        RentalException.Type.VALIDATION,
                        "Address could not be verified. Check street, city, postal code and country.",
                        "address.street"));
        address.assignCoordinates(coordinates);
    }

    private void require(boolean valid, RentalException.Type type, String message) {
        if (!valid) {
            throw new RentalException(type, message);
        }
    }
}

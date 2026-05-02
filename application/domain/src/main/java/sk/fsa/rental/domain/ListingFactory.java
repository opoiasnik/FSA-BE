package sk.fsa.rental.domain;

import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.service.GeocodingService;

public class ListingFactory {

    private final ListingRepository listingRepository;
    private final GeocodingService geocodingService;

    public ListingFactory(ListingRepository listingRepository, GeocodingService geocodingService) {
        this.listingRepository = listingRepository;
        this.geocodingService = geocodingService;
    }

    public Listing createListing(Listing listing, User owner) {
        require(listing != null,
                RentalException.Type.VALIDATION, "Listing must not be null.");
        require(owner != null,
                RentalException.Type.VALIDATION, "Owner must not be null.");

        listing.setOwner(owner);
        listing.validateForCreation();

        require(!listingRepository.existsByOwnerIdAndAddress(owner.getId(), listing.getAddress()),
                RentalException.Type.VALIDATION, "Owner already has a listing at this address.");

        Address address = listing.getAddress();
        if (address.getLat() == null || address.getLng() == null) {
            geocodingService.geocode(address).ifPresent(coords -> {
                address.setLat(coords.lat());
                address.setLng(coords.lng());
            });
        }

        return listing;
    }

    private void require(boolean valid, RentalException.Type type, String message) {
        if (!valid) {
            throw new RentalException(type, message);
        }
    }
}

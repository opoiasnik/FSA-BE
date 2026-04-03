package sk.fsa.rental.domain.facade;

import sk.fsa.rental.domain.Listing;

public interface ListingFacade {

    Listing createListing(Listing listing, String ownerEmail);

    Listing updateListing(Long listingId, Listing updatedListing, Long ownerId);

    void deleteListing(Long listingId, Long ownerId);

    Listing activateListing(Long listingId, Long ownerId);

    Listing getListingById(Long id);
}

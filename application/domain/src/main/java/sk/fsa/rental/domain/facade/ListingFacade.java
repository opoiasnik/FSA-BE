package sk.fsa.rental.domain.facade;

import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.User;

import java.util.List;

public interface ListingFacade {

    Listing createListing(Listing listing, User owner);

    Listing updateListing(Long listingId, Listing updatedListing, User editor);

    void deleteListing(Long listingId, User editor);

    Listing activateListing(Long listingId, User editor);

    Listing getListingById(Long id);

    List<Listing> getFeaturedListings();
}

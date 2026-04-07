package sk.fsa.rental.domain;

public class ListingFactory {

    public Listing createListing(Listing listing, User owner) {
        listing.setOwner(owner);
        listing.validateForCreation();
        return listing;
    }
}

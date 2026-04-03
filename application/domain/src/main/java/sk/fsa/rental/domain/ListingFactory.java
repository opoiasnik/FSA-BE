package sk.fsa.rental.domain;

public class ListingFactory {

    public Listing createListing(
            String title,
            String description,
            ListingType listingType,
            Price price,
            Address address,
            PropertyFeatures features
    ) {
        Listing listing = new Listing();
        listing.setTitle(title);
        listing.setDescription(description);
        listing.setListingType(listingType);
        listing.setPrice(price);
        listing.setAddress(address);
        listing.setFeatures(features);
        return listing;
    }
}

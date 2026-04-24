package sk.fsa.rental.domain.predicate.listing;

import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.ListingStatus;

import java.util.function.Predicate;

public final class IsListingInactivePredicate implements Predicate<Listing> {

    public static final IsListingInactivePredicate INSTANCE = new IsListingInactivePredicate();

    private IsListingInactivePredicate() {}

    @Override
    public boolean test(Listing listing) {
        return ListingStatus.INACTIVE.equals(listing.getStatus());
    }
}

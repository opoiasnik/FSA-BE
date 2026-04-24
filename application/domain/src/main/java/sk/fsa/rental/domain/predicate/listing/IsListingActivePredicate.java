package sk.fsa.rental.domain.predicate.listing;

import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.ListingStatus;

import java.util.function.Predicate;

public final class IsListingActivePredicate implements Predicate<Listing> {

    public static final IsListingActivePredicate INSTANCE = new IsListingActivePredicate();

    private IsListingActivePredicate() {}

    @Override
    public boolean test(Listing listing) {
        return ListingStatus.ACTIVE.equals(listing.getStatus());
    }
}

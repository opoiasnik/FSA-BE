package sk.fsa.rental.domain.predicate.listing;

import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.User;

import java.util.function.BiPredicate;

public final class IsListingVisibleToPredicate implements BiPredicate<Listing, User> {

    public static final IsListingVisibleToPredicate INSTANCE = new IsListingVisibleToPredicate();

    private IsListingVisibleToPredicate() {}

    @Override
    public boolean test(Listing listing, User requester) {
        return listing != null
                && (IsListingActivePredicate.INSTANCE.test(listing)
                || IsOwnedByPredicate.INSTANCE.test(listing.getOwner(), requester));
    }
}

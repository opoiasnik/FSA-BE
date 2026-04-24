package sk.fsa.rental.domain.predicate.listing;

import sk.fsa.rental.domain.Listing;

import java.util.function.Predicate;

public final class HasRequiredOwnerPredicate implements Predicate<Listing> {

    public static final HasRequiredOwnerPredicate INSTANCE = new HasRequiredOwnerPredicate();

    private HasRequiredOwnerPredicate() {}

    @Override
    public boolean test(Listing listing) {
        return listing.getOwner() != null;
    }
}

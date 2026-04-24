package sk.fsa.rental.domain.predicate.listing;

import sk.fsa.rental.domain.ListingType;

import java.util.function.Predicate;

public final class HasRequiredListingTypePredicate implements Predicate<ListingType> {

    public static final HasRequiredListingTypePredicate INSTANCE = new HasRequiredListingTypePredicate();

    private HasRequiredListingTypePredicate() {}

    @Override
    public boolean test(ListingType listingType) {
        return listingType != null;
    }
}

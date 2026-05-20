package sk.fsa.rental.domain.predicate.listing;

import sk.fsa.rental.domain.Address;

import java.util.function.Predicate;

public final class RequiresGeocodingPredicate implements Predicate<Address> {

    public static final RequiresGeocodingPredicate INSTANCE = new RequiresGeocodingPredicate();

    private RequiresGeocodingPredicate() {}

    @Override
    public boolean test(Address address) {
        return address != null && (address.getLat() == null || address.getLng() == null);
    }
}

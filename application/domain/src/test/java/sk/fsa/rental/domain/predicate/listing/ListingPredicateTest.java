package sk.fsa.rental.domain.predicate.listing;

import org.junit.jupiter.api.Test;
import sk.fsa.rental.domain.Address;
import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.UserRole;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListingPredicateTest {

    @Test
    void isListingActivePredicateAcceptsOnlyActiveListings() {
        User owner = user(1L, UserRole.OWNER);
        Listing active = listing(owner);
        Listing inactive = listing(owner);
        inactive.deactivate(owner);

        assertTrue(IsListingActivePredicate.INSTANCE.test(active));
        assertFalse(IsListingActivePredicate.INSTANCE.test(inactive));
        assertFalse(IsListingActivePredicate.INSTANCE.test(null));
    }

    @Test
    void isListingVisibleToPredicateAllowsAnonymousUserOnlyForActiveListings() {
        User owner = user(1L, UserRole.OWNER);
        Listing active = listing(owner);
        Listing inactive = listing(owner);
        inactive.deactivate(owner);

        assertTrue(IsListingVisibleToPredicate.INSTANCE.test(active, null));
        assertFalse(IsListingVisibleToPredicate.INSTANCE.test(inactive, null));
    }

    @Test
    void isListingVisibleToPredicateAllowsOwnerToSeeInactiveListing() {
        User owner = user(1L, UserRole.OWNER);
        Listing inactive = listing(owner);
        inactive.deactivate(owner);

        assertTrue(IsListingVisibleToPredicate.INSTANCE.test(inactive, user(1L, UserRole.OWNER)));
        assertFalse(IsListingVisibleToPredicate.INSTANCE.test(inactive, user(2L, UserRole.USER)));
    }

    @Test
    void isOwnedByPredicateRequiresBothIds() {
        assertTrue(IsOwnedByPredicate.INSTANCE.test(user(1L, UserRole.OWNER), user(1L, UserRole.OWNER)));
        assertFalse(IsOwnedByPredicate.INSTANCE.test(user(1L, UserRole.OWNER), user(2L, UserRole.OWNER)));
        assertFalse(IsOwnedByPredicate.INSTANCE.test(user(null, UserRole.OWNER), user(1L, UserRole.OWNER)));
        assertFalse(IsOwnedByPredicate.INSTANCE.test(null, user(1L, UserRole.OWNER)));
    }

    @Test
    void requiresGeocodingPredicateRequiresMissingLatitudeOrLongitude() {
        Address complete = address(48.72, 21.25);
        Address withoutLatitude = address(null, 21.25);
        Address withoutLongitude = address(48.72, null);

        assertFalse(RequiresGeocodingPredicate.INSTANCE.test(complete));
        assertTrue(RequiresGeocodingPredicate.INSTANCE.test(withoutLatitude));
        assertTrue(RequiresGeocodingPredicate.INSTANCE.test(withoutLongitude));
        assertFalse(RequiresGeocodingPredicate.INSTANCE.test(null));
    }

    private Listing listing(User owner) {
        Listing listing = new Listing();
        setField(listing, "owner", owner);
        return listing;
    }

    private User user(Long id, UserRole role) {
        User user = new User("user-" + id, "test", "user" + id + "@test.sk", role);
        setField(user, "id", id);
        return user;
    }

    private Address address(Double lat, Double lng) {
        Address address = new Address("Main 1", "Kosice", "04001", "Slovakia");
        address.setLat(lat);
        address.setLng(lng);
        return address;
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot set field " + fieldName, e);
        }
    }
}

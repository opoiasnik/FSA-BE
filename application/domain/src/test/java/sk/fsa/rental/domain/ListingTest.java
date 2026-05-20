package sk.fsa.rental.domain;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListingTest {

    @Test
    void newListingIsActiveByDefault() {
        Listing listing = new Listing();

        assertEquals(ListingStatus.ACTIVE, listing.getStatus());
        assertNotNull(listing.getCreatedAt());
    }

    @Test
    void deactivateChangesActiveListingToInactiveForOwner() {
        User owner = user(1L, UserRole.OWNER);
        Listing listing = listing(owner);

        listing.deactivate(owner);

        assertEquals(ListingStatus.INACTIVE, listing.getStatus());
    }

    @Test
    void deactivateFailsWhenEditorIsNotOwner() {
        Listing listing = listing(user(1L, UserRole.OWNER));

        RentalException ex = assertThrows(RentalException.class,
                () -> listing.deactivate(user(2L, UserRole.OWNER)));

        assertEquals(RentalException.Type.FORBIDDEN, ex.getType());
    }

    @Test
    void deactivateFailsWhenListingIsAlreadyInactive() {
        User owner = user(1L, UserRole.OWNER);
        Listing listing = listing(owner);
        listing.deactivate(owner);

        RentalException ex = assertThrows(RentalException.class, () -> listing.deactivate(owner));

        assertEquals(RentalException.Type.VALIDATION, ex.getType());
    }

    @Test
    void activateChangesInactiveListingToActiveForOwner() {
        User owner = user(1L, UserRole.OWNER);
        Listing listing = listing(owner);
        listing.deactivate(owner);

        listing.activate(owner);

        assertEquals(ListingStatus.ACTIVE, listing.getStatus());
    }

    @Test
    void activateFailsWhenListingIsAlreadyActive() {
        User owner = user(1L, UserRole.OWNER);
        Listing listing = listing(owner);

        RentalException ex = assertThrows(RentalException.class, () -> listing.activate(owner));

        assertEquals(RentalException.Type.VALIDATION, ex.getType());
    }

    @Test
    void validateForCreationRejectsNonOwnerRole() {
        Listing listing = listing(user(1L, UserRole.USER));

        RentalException ex = assertThrows(RentalException.class, listing::validateForCreation);

        assertEquals(RentalException.Type.FORBIDDEN, ex.getType());
    }

    private Listing listing(User owner) {
        Listing listing = new Listing();
        listing.setId(10L);
        listing.setTitle("City apartment");
        listing.setDescription("Bright apartment close to center.");
        listing.setListingType(ListingType.RENT);
        listing.setOwner(owner);
        listing.setAddress(new Address("Main 1", "Kosice", "04001", "Slovakia"));
        listing.setPrice(new Price(BigDecimal.valueOf(700), "EUR"));
        PropertyFeatures features = new PropertyFeatures();
        features.setPropertyType(PropertyType.APARTMENT);
        features.setArea(55.0);
        features.setRoomCount(2);
        listing.setFeatures(features);
        return listing;
    }

    private User user(Long id, UserRole role) {
        User user = new User("user-" + id, "test", "user" + id + "@test.sk", role);
        setField(user, "id", id);
        return user;
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

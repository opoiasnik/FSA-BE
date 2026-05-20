package sk.fsa.rental.domain;

import org.junit.jupiter.api.Test;
import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.service.GeocodingService;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListingFactoryTest {

    private final ListingRepository listingRepository = mock(ListingRepository.class);
    private final GeocodingService geocodingService = mock(GeocodingService.class);
    private final ListingFactory factory = new ListingFactory(listingRepository, geocodingService);

    @Test
    void createAssignsCoordinatesWhenAddressCanBeVerified() {
        User owner = user(1L, UserRole.OWNER);
        Listing listing = validListing();
        when(listingRepository.existsByOwnerIdAndAddress(owner.getId(), listing.getAddress())).thenReturn(false);
        when(geocodingService.geocode(listing.getAddress())).thenReturn(Optional.of(new Coordinates(48.7164, 21.2611)));

        Listing created = factory.create(listing, owner);

        assertEquals(48.7164, created.getAddress().getLat());
        assertEquals(21.2611, created.getAddress().getLng());
    }

    @Test
    void createRejectsAddressThatCannotBeVerified() {
        User owner = user(1L, UserRole.OWNER);
        Listing listing = validListing();
        when(listingRepository.existsByOwnerIdAndAddress(owner.getId(), listing.getAddress())).thenReturn(false);
        when(geocodingService.geocode(listing.getAddress())).thenReturn(Optional.empty());

        RentalException ex = assertThrows(RentalException.class, () -> factory.create(listing, owner));

        assertEquals(RentalException.Type.VALIDATION, ex.getType());
        assertEquals("address.street", ex.getField());
    }

    @Test
    void createKeepsExistingCoordinatesWithoutCallingGeocoding() {
        User owner = user(1L, UserRole.OWNER);
        Listing listing = validListing();
        listing.getAddress().setLat(48.7164);
        listing.getAddress().setLng(21.2611);
        when(listingRepository.existsByOwnerIdAndAddress(owner.getId(), listing.getAddress())).thenReturn(false);

        Listing created = factory.create(listing, owner);

        assertEquals(48.7164, created.getAddress().getLat());
        assertEquals(21.2611, created.getAddress().getLng());
        verify(geocodingService, never()).geocode(listing.getAddress());
    }

    private Listing validListing() {
        Listing listing = new Listing();
        listing.setTitle("City apartment");
        listing.setDescription("Bright apartment close to center.");
        listing.setListingType(ListingType.RENT);
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

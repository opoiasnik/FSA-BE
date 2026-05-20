package sk.fsa.rental.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.ListingFactory;
import sk.fsa.rental.domain.ListingViewEvent;
import sk.fsa.rental.domain.PhotoFactory;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.UserRole;
import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.repository.ListingViewEventRepository;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListingServiceTest {

    @Mock
    private ListingRepository listingRepository;
    @Mock
    private ListingFactory listingFactory;
    @Mock
    private ListingViewEventRepository listingViewEventRepository;
    @Mock
    private PhotoFactory photoFactory;
    @InjectMocks
    private ListingService service;

    @Test
    void getVisibleByIdAllowsOwnerToOpenInactiveListing() {
        User owner = user(1L, UserRole.OWNER);
        Listing listing = listing(100L, owner);
        listing.deactivate(owner);
        when(listingRepository.findById(100L)).thenReturn(Optional.of(listing));

        Listing result = service.getVisibleById(100L, user(1L, UserRole.OWNER));

        assertEquals(100L, result.getId());
    }

    @Test
    void getVisibleByIdHidesInactiveListingFromOtherUsers() {
        User owner = user(1L, UserRole.OWNER);
        Listing listing = listing(100L, owner);
        listing.deactivate(owner);
        when(listingRepository.findById(100L)).thenReturn(Optional.of(listing));

        RentalException ex = assertThrows(RentalException.class,
                () -> service.getVisibleById(100L, user(2L, UserRole.USER)));

        assertEquals(RentalException.Type.NOT_FOUND, ex.getType());
    }

    @Test
    void deactivateDelegatesBusinessRuleToListingAndPersists() {
        User owner = user(1L, UserRole.OWNER);
        Listing listing = listing(100L, owner);
        when(listingRepository.findById(100L)).thenReturn(Optional.of(listing));
        when(listingRepository.save(listing)).thenReturn(listing);

        Listing saved = service.deactivate(100L, owner);

        assertEquals(listing, saved);
        verify(listingRepository).save(listing);
    }

    @Test
    void recordViewSkipsOwnerViews() {
        Listing listing = listing(100L, user(1L, UserRole.OWNER));
        when(listingRepository.findById(100L)).thenReturn(Optional.of(listing));

        service.recordView(100L, 1L);

        verify(listingViewEventRepository, never()).save(any());
    }

    @Test
    void recordViewRejectsInactiveListingForVisitor() {
        User owner = user(1L, UserRole.OWNER);
        Listing listing = listing(100L, owner);
        listing.deactivate(owner);
        when(listingRepository.findById(100L)).thenReturn(Optional.of(listing));

        RentalException ex = assertThrows(RentalException.class, () -> service.recordView(100L, 2L));

        assertEquals(RentalException.Type.NOT_FOUND, ex.getType());
        verify(listingViewEventRepository, never()).save(any());
    }

    @Test
    void recordViewPersistsVisitorViewForActiveListing() {
        Listing listing = listing(100L, user(1L, UserRole.OWNER));
        when(listingRepository.findById(100L)).thenReturn(Optional.of(listing));

        service.recordView(100L, 2L);

        verify(listingViewEventRepository).save(any(ListingViewEvent.class));
    }

    private Listing listing(Long id, User owner) {
        Listing listing = new Listing();
        listing.setId(id);
        setField(listing, "owner", owner);
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

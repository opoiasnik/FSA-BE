package sk.fsa.rental.domain;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ViewingRequestTest {

    @Test
    void validateForCreationAcceptsActiveListingWithDifferentRequester() {
        ViewingRequest request = viewingRequest(listing(user(1L, UserRole.OWNER)), user(2L, UserRole.USER));
        request.setRequestedDate(new Date());

        request.validateForCreation();

        assertEquals(ViewingStatus.PENDING, request.getStatus());
    }

    @Test
    void validateForCreationRejectsInactiveListing() {
        User owner = user(1L, UserRole.OWNER);
        Listing listing = listing(owner);
        listing.deactivate(owner);
        ViewingRequest request = viewingRequest(listing, user(2L, UserRole.USER));
        request.setRequestedDate(new Date());

        RentalException ex = assertThrows(RentalException.class, request::validateForCreation);

        assertEquals(RentalException.Type.VALIDATION, ex.getType());
    }

    @Test
    void validateForCreationRejectsOwnListingRequest() {
        User owner = user(1L, UserRole.OWNER);
        ViewingRequest request = viewingRequest(listing(owner), user(1L, UserRole.USER));
        request.setRequestedDate(new Date());

        RentalException ex = assertThrows(RentalException.class, request::validateForCreation);

        assertEquals(RentalException.Type.VALIDATION, ex.getType());
    }

    @Test
    void approveAndRejectRequirePendingStatus() {
        User owner = user(1L, UserRole.OWNER);
        ViewingRequest request = viewingRequest(listing(owner), user(2L, UserRole.USER));

        request.approve(owner);

        assertEquals(ViewingStatus.APPROVED, request.getStatus());
        RentalException ex = assertThrows(RentalException.class, () -> request.reject(owner));
        assertEquals(RentalException.Type.VALIDATION, ex.getType());
    }

    @Test
    void cancelAllowsRequesterForPendingOrApprovedRequest() {
        User owner = user(1L, UserRole.OWNER);
        User requester = user(2L, UserRole.USER);
        ViewingRequest request = viewingRequest(listing(owner), requester);
        request.approve(owner);

        request.cancel(requester);

        assertEquals(ViewingStatus.CANCELLED, request.getStatus());
    }

    @Test
    void cancelFailsForOwner() {
        User owner = user(1L, UserRole.OWNER);
        ViewingRequest request = viewingRequest(listing(owner), user(2L, UserRole.USER));

        RentalException ex = assertThrows(RentalException.class, () -> request.cancel(owner));

        assertEquals(RentalException.Type.FORBIDDEN, ex.getType());
    }

    private ViewingRequest viewingRequest(Listing listing, User requester) {
        ViewingRequest request = new ViewingRequest();
        request.assignParticipants(listing, requester);
        return request;
    }

    private Listing listing(User owner) {
        Listing listing = new Listing();
        listing.setOwner(owner);
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

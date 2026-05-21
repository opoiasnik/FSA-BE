package sk.fsa.rental.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.UserRole;
import sk.fsa.rental.domain.ViewingRequest;
import sk.fsa.rental.domain.ViewingStatus;
import sk.fsa.rental.domain.facade.NotificationEmailFacade;
import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.repository.ViewingRequestRepository;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ViewingRequestServiceTest {

    @Mock
    private ViewingRequestRepository viewingRequestRepository;
    @Mock
    private ListingRepository listingRepository;
    @Mock
    private NotificationEmailFacade notificationEmailFacade;
    @InjectMocks
    private ViewingRequestService service;

    @Test
    void createRequestRejectsDuplicateActiveRequest() {
        User requester = user(2L, UserRole.USER);
        Listing listing = listing(100L, user(1L, UserRole.OWNER));
        ViewingRequest existing = request(1L, listing, requester, date(1000L));
        when(listingRepository.findById(100L)).thenReturn(Optional.of(listing));
        when(viewingRequestRepository.findByListingIdAndRequesterId(100L, 2L)).thenReturn(List.of(existing));

        ViewingRequest newRequest = new ViewingRequest(date(2000L), null);

        RentalException ex = assertThrows(RentalException.class,
                () -> service.createRequest(newRequest, 100L, requester));

        assertEquals(RentalException.Type.VALIDATION, ex.getType());
        verify(viewingRequestRepository, never()).save(any());
        verify(notificationEmailFacade, never()).viewingRequestCreated(any());
    }

    @Test
    void createRequestAllowsNewRequestWhenPreviousRequestWasRejected() {
        User requester = user(2L, UserRole.USER);
        User owner = user(1L, UserRole.OWNER);
        Listing listing = listing(100L, owner);
        ViewingRequest rejected = request(1L, listing, requester, date(1000L));
        rejected.reject(owner);
        ViewingRequest newRequest = new ViewingRequest(date(2000L), null);
        when(listingRepository.findById(100L)).thenReturn(Optional.of(listing));
        when(viewingRequestRepository.findByListingIdAndRequesterId(100L, 2L)).thenReturn(List.of(rejected));
        when(viewingRequestRepository.save(newRequest)).thenReturn(newRequest);

        ViewingRequest saved = service.createRequest(newRequest, 100L, requester);

        assertEquals(ViewingStatus.PENDING, saved.getStatus());
        verify(viewingRequestRepository).save(newRequest);
        verify(notificationEmailFacade).viewingRequestCreated(saved);
    }

    @Test
    void approveSavesAndNotifiesRequester() {
        User owner = user(1L, UserRole.OWNER);
        ViewingRequest existing = request(10L, listing(100L, owner), user(2L, UserRole.USER), date(1000L));
        when(viewingRequestRepository.findById(10L)).thenReturn(Optional.of(existing));
        when(viewingRequestRepository.save(existing)).thenReturn(existing);

        ViewingRequest saved = service.approve(10L, owner);

        assertEquals(ViewingStatus.APPROVED, saved.getStatus());
        verify(notificationEmailFacade).viewingStatusChanged(saved);
    }

    @Test
    void cancelSavesAndNotifiesOwner() {
        User owner = user(1L, UserRole.OWNER);
        User requester = user(2L, UserRole.USER);
        ViewingRequest existing = request(10L, listing(100L, owner), requester, date(1000L));
        when(viewingRequestRepository.findById(10L)).thenReturn(Optional.of(existing));
        when(viewingRequestRepository.save(existing)).thenReturn(existing);

        ViewingRequest saved = service.cancel(10L, requester);

        assertEquals(ViewingStatus.CANCELLED, saved.getStatus());
        verify(notificationEmailFacade).viewingCancelled(saved);
    }

    @Test
    void listByOwnerReturnsPendingFirstThenReviewedNewestFirst() {
        User owner = user(1L, UserRole.OWNER);
        User requester = user(2L, UserRole.USER);
        Listing listing = listing(100L, owner);
        ViewingRequest oldPending = request(1L, listing, requester, date(1000L));
        ViewingRequest newPending = request(2L, listing, requester, date(3000L));
        ViewingRequest approved = request(3L, listing, requester, date(5000L));
        approved.approve(owner);
        ViewingRequest rejected = request(4L, listing, requester, date(6000L));
        rejected.reject(owner);
        ViewingRequest cancelled = request(5L, listing, requester, date(7000L));
        cancelled.cancel(requester);
        when(viewingRequestRepository.findByOwnerId(1L))
                .thenReturn(List.of(cancelled, rejected, approved, oldPending, newPending));

        List<ViewingRequest> result = service.listByOwner(1L);

        assertEquals(List.of(2L, 1L, 3L, 4L, 5L), result.stream().map(ViewingRequest::getId).toList());
    }

    private ViewingRequest request(Long id, Listing listing, User requester, Date requestedDate) {
        ViewingRequest request = new ViewingRequest(requestedDate, null);
        setField(request, "id", id);
        request.assignParticipants(listing, requester);
        return request;
    }

    private Listing listing(Long id, User owner) {
        Listing listing = new Listing();
        setField(listing, "id", id);
        setField(listing, "owner", owner);
        return listing;
    }

    private User user(Long id, UserRole role) {
        User user = new User("user-" + id, "test", "user" + id + "@test.sk", role);
        setField(user, "id", id);
        return user;
    }

    private Date date(long time) {
        return new Date(time);
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

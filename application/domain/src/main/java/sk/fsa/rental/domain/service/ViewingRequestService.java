package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.ViewingRequest;
import sk.fsa.rental.domain.ViewingStatus;
import sk.fsa.rental.domain.facade.NotificationEmailFacade;
import sk.fsa.rental.domain.facade.ViewingRequestFacade;
import sk.fsa.rental.domain.predicate.viewingrequest.IsViewingActivePredicate;
import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.repository.ViewingRequestRepository;

import java.util.Comparator;
import java.util.List;
public class ViewingRequestService implements ViewingRequestFacade {

    private final ViewingRequestRepository viewingRequestRepository;
    private final ListingRepository listingRepository;
    private final NotificationEmailFacade notificationEmailFacade;

    public ViewingRequestService(ViewingRequestRepository viewingRequestRepository,
                                  ListingRepository listingRepository,
                                  NotificationEmailFacade notificationEmailFacade) {
        this.viewingRequestRepository = viewingRequestRepository;
        this.listingRepository = listingRepository;
        this.notificationEmailFacade = notificationEmailFacade;
    }

    @Override
    public ViewingRequest createRequest(ViewingRequest viewingRequest, Long listingId, User requester) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Listing not found."));

        viewingRequest.assignParticipants(listing, requester);
        viewingRequest.validateForCreation();
        requireNoActiveRequest(listingId, requester.getId());
        ViewingRequest saved = viewingRequestRepository.save(viewingRequest);
        notificationEmailFacade.viewingRequestCreated(saved);
        return saved;
    }

    @Override
    public ViewingRequest approve(Long viewingId, User editor) {
        ViewingRequest existing = findOrThrow(viewingId);
        existing.approve(editor);
        ViewingRequest saved = viewingRequestRepository.save(existing);
        notificationEmailFacade.viewingStatusChanged(saved);
        return saved;
    }

    @Override
    public ViewingRequest reject(Long viewingId, User editor) {
        ViewingRequest existing = findOrThrow(viewingId);
        existing.reject(editor);
        ViewingRequest saved = viewingRequestRepository.save(existing);
        notificationEmailFacade.viewingStatusChanged(saved);
        return saved;
    }

    @Override
    public ViewingRequest cancel(Long viewingId, User editor) {
        ViewingRequest existing = findOrThrow(viewingId);
        existing.cancel(editor);
        ViewingRequest saved = viewingRequestRepository.save(existing);
        notificationEmailFacade.viewingCancelled(saved);
        return saved;
    }

    @Override
    public List<ViewingRequest> listByRequester(Long requesterId) {
        return sortForReview(viewingRequestRepository.findByRequesterId(requesterId));
    }

    @Override
    public List<ViewingRequest> listByOwner(Long ownerId) {
        return sortForReview(viewingRequestRepository.findByOwnerId(ownerId));
    }

    private ViewingRequest findOrThrow(Long viewingId) {
        return viewingRequestRepository.findById(viewingId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Viewing request not found."));
    }

    private void requireNoActiveRequest(Long listingId, Long requesterId) {
        boolean hasActiveRequest = viewingRequestRepository.findByListingIdAndRequesterId(listingId, requesterId).stream()
                .anyMatch(viewingRequest -> IsViewingActivePredicate.INSTANCE.test(viewingRequest.getStatus()));
        if (hasActiveRequest) {
            throw new RentalException(RentalException.Type.VALIDATION,
                    "You already have an active viewing request for this listing.");
        }
    }

    private List<ViewingRequest> sortForReview(List<ViewingRequest> requests) {
        return requests.stream()
                .sorted(Comparator
                        .comparingInt((ViewingRequest request) -> statusPriority(request.getStatus()))
                        .thenComparing(ViewingRequest::getRequestedDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }

    private int statusPriority(ViewingStatus status) {
        return switch (status) {
            case PENDING -> 0;
            case APPROVED -> 1;
            case REJECTED -> 2;
            case CANCELLED -> 3;
        };
    }
}

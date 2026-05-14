package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.ViewingRequest;
import sk.fsa.rental.domain.facade.ViewingRequestFacade;
import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.repository.ViewingRequestRepository;

import java.util.List;
public class ViewingRequestService implements ViewingRequestFacade {

    private final ViewingRequestRepository viewingRequestRepository;
    private final ListingRepository listingRepository;

    public ViewingRequestService(ViewingRequestRepository viewingRequestRepository,
                                  ListingRepository listingRepository) {
        this.viewingRequestRepository = viewingRequestRepository;
        this.listingRepository = listingRepository;
    }

    @Override
    public ViewingRequest createRequest(ViewingRequest viewingRequest, Long listingId, User requester) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Listing not found."));

        viewingRequest.assignParticipants(listing, requester);
        viewingRequest.validateForCreation();
        return viewingRequestRepository.save(viewingRequest);
    }

    @Override
    public ViewingRequest approve(Long viewingId, User editor) {
        ViewingRequest existing = findOrThrow(viewingId);
        existing.approve(editor);
        return viewingRequestRepository.save(existing);
    }

    @Override
    public ViewingRequest reject(Long viewingId, User editor) {
        ViewingRequest existing = findOrThrow(viewingId);
        existing.reject(editor);
        return viewingRequestRepository.save(existing);
    }

    @Override
    public ViewingRequest cancel(Long viewingId, User editor) {
        ViewingRequest existing = findOrThrow(viewingId);
        existing.cancel(editor);
        return viewingRequestRepository.save(existing);
    }

    @Override
    public List<ViewingRequest> listByRequester(Long requesterId) {
        return viewingRequestRepository.findByRequesterId(requesterId);
    }

    @Override
    public List<ViewingRequest> listByOwner(Long ownerId) {
        return viewingRequestRepository.findByOwnerId(ownerId);
    }

    private ViewingRequest findOrThrow(Long viewingId) {
        return viewingRequestRepository.findById(viewingId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Viewing request not found."));
    }
}

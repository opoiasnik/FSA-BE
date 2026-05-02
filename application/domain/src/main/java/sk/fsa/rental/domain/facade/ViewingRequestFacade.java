package sk.fsa.rental.domain.facade;

import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.ViewingRequest;

import java.util.List;

public interface ViewingRequestFacade {
    ViewingRequest createRequest(ViewingRequest viewingRequest, Long listingId, User requester);
    ViewingRequest approve(Long viewingId, User editor);
    ViewingRequest reject(Long viewingId, User editor);
    ViewingRequest cancel(Long viewingId, User editor);
    List<ViewingRequest> listByRequester(Long requesterId);
    List<ViewingRequest> listByOwner(Long ownerId);
}

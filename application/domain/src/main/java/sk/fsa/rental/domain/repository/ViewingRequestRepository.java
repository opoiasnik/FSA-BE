package sk.fsa.rental.domain.repository;

import sk.fsa.rental.domain.ViewingRequest;

import java.util.List;
import java.util.Optional;

public interface ViewingRequestRepository {
    ViewingRequest save(ViewingRequest viewingRequest);
    Optional<ViewingRequest> findById(Long id);
    List<ViewingRequest> findByRequesterId(Long requesterId);
    List<ViewingRequest> findByOwnerId(Long ownerId);
    List<ViewingRequest> findByListingId(Long listingId);
    List<ViewingRequest> findByListingIdAndRequesterId(Long listingId, Long requesterId);
}

package sk.fsa.rental.domain.repository;

import sk.fsa.rental.domain.ViewingRequest;

import java.util.Collection;
import java.util.Optional;

public interface ViewingRequestRepository {
    void createViewingRequest(ViewingRequest viewingRequest);
    Optional<ViewingRequest> findById(Long id);
    Collection<ViewingRequest> findByListingId(Long listingId);
    Collection<ViewingRequest> findAll();
    void deleteViewingRequest(Long id);
}

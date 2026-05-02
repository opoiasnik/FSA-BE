package sk.fsa.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.fsa.rental.domain.ViewingRequest;

import java.util.List;

interface ViewingRequestSpringDataRepository extends JpaRepository<ViewingRequest, Long> {
    List<ViewingRequest> findByRequesterId(Long requesterId);
    List<ViewingRequest> findByOwnerId(Long ownerId);
    List<ViewingRequest> findByListingId(Long listingId);
}

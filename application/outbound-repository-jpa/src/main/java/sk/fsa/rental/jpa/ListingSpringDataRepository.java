package sk.fsa.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.ListingStatus;

import java.util.List;

interface ListingSpringDataRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByStatus(ListingStatus status);
    List<Listing> findByOwnerId(Long ownerId);
}

package sk.fsa.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.fsa.rental.domain.ListingViewEvent;

import java.util.Date;
import java.util.List;

public interface ListingViewEventSpringDataRepository extends JpaRepository<ListingViewEvent, Long> {

    long countByOwnerId(Long ownerId);

    List<ListingViewEvent> findByOwnerIdAndViewedAtAfter(Long ownerId, Date since);
}

package sk.fsa.rental.domain.repository;

import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.ListingStatus;

import java.util.List;
import java.util.Optional;

public interface ListingRepository {
    Listing save(Listing listing);
    Optional<Listing> findById(Long id);
    List<Listing> findByStatus(ListingStatus status);
    List<Listing> findByOwnerId(Long ownerId);
    void deleteById(Long id);
}

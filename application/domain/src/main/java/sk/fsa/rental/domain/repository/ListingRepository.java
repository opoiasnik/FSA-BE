package sk.fsa.rental.domain.repository;

import sk.fsa.rental.domain.Address;
import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.ListingSearchFilters;
import sk.fsa.rental.domain.ListingSearchResult;

import java.util.List;
import java.util.Optional;

public interface ListingRepository {
    Listing save(Listing listing);
    Optional<Listing> findById(Long id);
    List<Listing> findByOwnerId(Long ownerId);
    boolean existsByOwnerIdAndAddress(Long ownerId, Address address);
    ListingSearchResult search(ListingSearchFilters filters);
    List<Listing> findTopViewed(ListingSearchFilters filters);
}

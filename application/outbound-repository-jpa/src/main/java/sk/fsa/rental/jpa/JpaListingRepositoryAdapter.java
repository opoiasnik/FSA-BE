package sk.fsa.rental.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import sk.fsa.rental.domain.Address;
import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.ListingSearchFilters;
import sk.fsa.rental.domain.ListingSearchResult;
import sk.fsa.rental.domain.ListingStatus;
import sk.fsa.rental.domain.repository.ListingRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaListingRepositoryAdapter implements ListingRepository {

    private final ListingSpringDataRepository listingSpringDataRepository;

    public JpaListingRepositoryAdapter(ListingSpringDataRepository listingSpringDataRepository) {
        this.listingSpringDataRepository = listingSpringDataRepository;
    }

    @Override
    public Listing save(Listing listing) {
        return listingSpringDataRepository.save(listing);
    }

    @Override
    public Optional<Listing> findById(Long id) {
        return listingSpringDataRepository.findById(id);
    }

    @Override
    public List<Listing> findByStatus(ListingStatus status) {
        return listingSpringDataRepository.findByStatus(status);
    }

    @Override
    public List<Listing> findByOwnerId(Long ownerId) {
        return listingSpringDataRepository.findByOwnerId(ownerId);
    }

    @Override
    public void deleteById(Long id) {
        listingSpringDataRepository.deleteById(id);
    }

    @Override
    public boolean existsByOwnerIdAndAddress(Long ownerId, Address address) {
        return listingSpringDataRepository.existsByOwnerIdAndAddress(ownerId, address);
    }

    @Override
    public ListingSearchResult search(ListingSearchFilters filters) {
        int effectivePage = Math.max(filters.page(), 0);
        int effectiveSize = filters.size() < 1 ? 10 : Math.min(filters.size(), 100);
        Pageable pageable = PageRequest.of(effectivePage, effectiveSize);

        Page<Listing> page = listingSpringDataRepository.search(
                filters.city(), filters.listingType(), filters.propertyType(), pageable);

        return new ListingSearchResult(page.getContent(), effectivePage, effectiveSize, page.getTotalElements());
    }
}

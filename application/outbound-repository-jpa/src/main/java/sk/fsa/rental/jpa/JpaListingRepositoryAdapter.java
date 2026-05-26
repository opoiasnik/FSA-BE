package sk.fsa.rental.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import sk.fsa.rental.domain.Address;
import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.ListingSearchFilters;
import sk.fsa.rental.domain.ListingSearchResult;
import sk.fsa.rental.domain.ListingStatus;
import sk.fsa.rental.domain.SortBy;
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
        return listingSpringDataRepository.existsByOwnerIdAndAddressIdentity(
                ownerId,
                address.getStreet(),
                address.getCity(),
                address.getPostalCode(),
                address.getCountry());
    }

    @Override
    public ListingSearchResult search(ListingSearchFilters filters) {
        int effectivePage = Math.max(filters.page(), 0);
        int effectiveSize = filters.size() < 1 ? 10 : Math.min(filters.size(), 100);
        Pageable pageable = PageRequest.of(effectivePage, effectiveSize, resolveSort(filters.sortBy()));

        Page<Listing> page = listingSpringDataRepository.findAll(
                ListingSpecifications.from(filters), pageable);

        return new ListingSearchResult(page.getContent(), effectivePage, effectiveSize, page.getTotalElements());
    }

    @Override
    public List<Listing> findTopViewed(ListingSearchFilters filters) {
        int effectiveSize = filters.size() < 1 ? 5 : Math.min(filters.size(), 100);
        Pageable pageable = PageRequest.of(0, effectiveSize);
        return listingSpringDataRepository.findAll(ListingSpecifications.topViewed(filters), pageable).getContent();
    }

    private Sort resolveSort(SortBy sortBy) {
        return switch (sortBy) {
            case PRICE_ASC  -> Sort.by(Sort.Direction.ASC,  "price.amount");
            case PRICE_DESC -> Sort.by(Sort.Direction.DESC, "price.amount");
            case AREA_ASC   -> Sort.by(Sort.Direction.ASC,  "features.area");
            case AREA_DESC  -> Sort.by(Sort.Direction.DESC, "features.area");
            default         -> Sort.by(Sort.Direction.DESC, "createdAt");
        };
    }

}

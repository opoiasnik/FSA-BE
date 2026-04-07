package sk.fsa.rental.jpa;

import org.springframework.stereotype.Repository;
import sk.fsa.rental.domain.Address;
import sk.fsa.rental.domain.Listing;
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
}

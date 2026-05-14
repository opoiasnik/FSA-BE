package sk.fsa.rental.jpa;

import org.springframework.stereotype.Repository;
import sk.fsa.rental.domain.ListingViewEvent;
import sk.fsa.rental.domain.repository.ListingViewEventRepository;

import java.util.Date;
import java.util.List;

@Repository
public class JpaListingViewEventRepositoryAdapter implements ListingViewEventRepository {

    private final ListingViewEventSpringDataRepository springDataRepository;

    public JpaListingViewEventRepositoryAdapter(ListingViewEventSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public ListingViewEvent save(ListingViewEvent event) {
        return springDataRepository.save(event);
    }

    @Override
    public long countByOwnerId(Long ownerId) {
        return springDataRepository.countByOwnerId(ownerId);
    }

    @Override
    public List<ListingViewEvent> findByOwnerIdAndViewedAtAfter(Long ownerId, Date since) {
        return springDataRepository.findByOwnerIdAndViewedAtAfter(ownerId, since);
    }
}

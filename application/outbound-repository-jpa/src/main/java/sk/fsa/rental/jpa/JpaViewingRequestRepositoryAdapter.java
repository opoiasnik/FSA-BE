package sk.fsa.rental.jpa;

import org.springframework.stereotype.Repository;
import sk.fsa.rental.domain.ViewingRequest;
import sk.fsa.rental.domain.repository.ViewingRequestRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaViewingRequestRepositoryAdapter implements ViewingRequestRepository {

    private final ViewingRequestSpringDataRepository viewingRequestSpringDataRepository;

    public JpaViewingRequestRepositoryAdapter(ViewingRequestSpringDataRepository viewingRequestSpringDataRepository) {
        this.viewingRequestSpringDataRepository = viewingRequestSpringDataRepository;
    }

    @Override
    public ViewingRequest save(ViewingRequest viewingRequest) {
        return viewingRequestSpringDataRepository.save(viewingRequest);
    }

    @Override
    public Optional<ViewingRequest> findById(Long id) {
        return viewingRequestSpringDataRepository.findById(id);
    }

    @Override
    public List<ViewingRequest> findByRequesterId(Long requesterId) {
        return viewingRequestSpringDataRepository.findByRequesterId(requesterId);
    }

    @Override
    public List<ViewingRequest> findByOwnerId(Long ownerId) {
        return viewingRequestSpringDataRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<ViewingRequest> findByListingId(Long listingId) {
        return viewingRequestSpringDataRepository.findByListingId(listingId);
    }
}

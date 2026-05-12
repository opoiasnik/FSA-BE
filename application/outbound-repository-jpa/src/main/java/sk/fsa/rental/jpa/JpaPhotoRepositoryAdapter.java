package sk.fsa.rental.jpa;

import org.springframework.stereotype.Repository;
import sk.fsa.rental.domain.Photo;
import sk.fsa.rental.domain.repository.PhotoRepository;

import java.util.Optional;

@Repository
public class JpaPhotoRepositoryAdapter implements PhotoRepository {

    private final PhotoSpringDataRepository photoSpringDataRepository;

    public JpaPhotoRepositoryAdapter(PhotoSpringDataRepository photoSpringDataRepository) {
        this.photoSpringDataRepository = photoSpringDataRepository;
    }

    @Override
    public Photo save(Photo photo) {
        return photoSpringDataRepository.save(photo);
    }

    @Override
    public Optional<Photo> findById(Long id) {
        return photoSpringDataRepository.findById(id);
    }
}

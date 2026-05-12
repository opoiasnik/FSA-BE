package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.Photo;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.facade.PhotoFacade;
import sk.fsa.rental.domain.repository.PhotoRepository;

public class PhotoService implements PhotoFacade {

    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public Photo getById(Long photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Photo not found."));
    }
}

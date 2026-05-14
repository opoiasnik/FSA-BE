package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.Photo;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.PhotoFacade;
import sk.fsa.rental.domain.repository.PhotoRepository;

public class PhotoService implements PhotoFacade {

    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public Photo getById(Long photoId, User requester) {
        Photo photo = findById(photoId);
        require(photo.canBeViewedBy(requester),
                RentalException.Type.FORBIDDEN, "Photo is not available for this user.");
        return photo;
    }

    @Override
    public Photo getPublicCoverById(Long photoId) {
        Photo photo = findById(photoId);
        require(photo.isPublicCover(),
                RentalException.Type.NOT_FOUND, "Photo not found.");
        return photo;
    }

    private Photo findById(Long photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Photo not found."));
    }

    private void require(boolean valid, RentalException.Type type, String message) {
        if (!valid) {
            throw new RentalException(type, message);
        }
    }
}

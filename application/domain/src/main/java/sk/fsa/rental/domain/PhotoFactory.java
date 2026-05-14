package sk.fsa.rental.domain;

import sk.fsa.rental.domain.predicate.photo.HasImageContentTypePredicate;
import sk.fsa.rental.domain.predicate.photo.HasPhotoDataPredicate;

public class PhotoFactory {

    public Photo create(byte[] data, String contentType, String originalFilename,
                        String altText, Integer position) {
        require(HasPhotoDataPredicate.INSTANCE.test(data),
                "Photo file is required.");
        require(HasImageContentTypePredicate.INSTANCE.test(contentType),
                "Only image files can be uploaded.");
        return new Photo(data, contentType, originalFilename, altText, position);
    }

    private void require(boolean valid, String message) {
        if (!valid) {
            throw new RentalException(RentalException.Type.VALIDATION, message, "file");
        }
    }
}

package sk.fsa.rental.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import sk.fsa.rental.domain.Photo;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.PhotoFacade;
import sk.fsa.rental.rest.api.PhotoApi;
import sk.fsa.rental.security.CurrentUserDetailService;

@RestController
public class PhotoRestController implements PhotoApi {

    private final PhotoFacade photoFacade;
    private final CurrentUserDetailService currentUserDetailService;

    public PhotoRestController(PhotoFacade photoFacade, CurrentUserDetailService currentUserDetailService) {
        this.photoFacade = photoFacade;
        this.currentUserDetailService = currentUserDetailService;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Resource> getPhotoContent(Long photoId) {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        return toImageResponse(photoFacade.getById(photoId, currentUser));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Resource> getPublicCoverPhotoContent(Long photoId) {
        return toImageResponse(photoFacade.getPublicCoverById(photoId));
    }

    private ResponseEntity<Resource> toImageResponse(Photo photo) {
        byte[] data = photo.getData();
        if (data == null || data.length == 0) {
            throw new RentalException(RentalException.Type.NOT_FOUND, "Photo content not found.");
        }
        String contentType = photo.getContentType() != null
                ? photo.getContentType()
                : MediaType.APPLICATION_OCTET_STREAM_VALUE;
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }
}

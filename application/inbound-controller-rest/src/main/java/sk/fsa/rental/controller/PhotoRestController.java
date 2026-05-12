package sk.fsa.rental.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sk.fsa.rental.domain.Photo;
import sk.fsa.rental.domain.facade.PhotoFacade;

@RestController
public class PhotoRestController {

    private final PhotoFacade photoFacade;

    public PhotoRestController(PhotoFacade photoFacade) {
        this.photoFacade = photoFacade;
    }

    @GetMapping("/api/photos/{photoId}/content")
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> getPhotoContent(@PathVariable Long photoId) {
        Photo photo = photoFacade.getById(photoId);
        String contentType = photo.getContentType() != null
                ? photo.getContentType()
                : MediaType.APPLICATION_OCTET_STREAM_VALUE;
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(photo.getData());
    }
}

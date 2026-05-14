package sk.fsa.rental.domain.facade;

import sk.fsa.rental.domain.Photo;
import sk.fsa.rental.domain.User;

public interface PhotoFacade {
    Photo getById(Long photoId, User requester);

    Photo getPublicCoverById(Long photoId);
}

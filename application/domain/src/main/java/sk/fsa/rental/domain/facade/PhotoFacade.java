package sk.fsa.rental.domain.facade;

import sk.fsa.rental.domain.Photo;

public interface PhotoFacade {
    Photo getById(Long photoId);
}

package sk.fsa.rental.domain.repository;

import sk.fsa.rental.domain.Photo;

import java.util.Optional;

public interface PhotoRepository {
    Photo save(Photo photo);
    Optional<Photo> findById(Long id);
}

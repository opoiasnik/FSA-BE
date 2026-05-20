package sk.fsa.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.fsa.rental.domain.Photo;

interface PhotoSpringDataRepository extends JpaRepository<Photo, Long> {
}

package sk.fsa.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.fsa.rental.domain.Favorite;

import java.util.List;

interface FavoriteSpringDataRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);
    long countByListingOwnerId(Long ownerId);
}

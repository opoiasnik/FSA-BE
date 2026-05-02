package sk.fsa.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.fsa.rental.domain.Favorite;

import java.util.List;
import java.util.Optional;

interface FavoriteSpringDataRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserIdAndListingId(Long userId, Long listingId);
    List<Favorite> findByUserId(Long userId);
}

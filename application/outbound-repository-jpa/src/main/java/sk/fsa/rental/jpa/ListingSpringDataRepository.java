package sk.fsa.rental.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sk.fsa.rental.domain.Address;
import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.ListingStatus;
import sk.fsa.rental.domain.ListingType;
import sk.fsa.rental.domain.PropertyType;

import java.util.List;

interface ListingSpringDataRepository extends JpaRepository<Listing, Long> {

    List<Listing> findByStatus(ListingStatus status);

    List<Listing> findByOwnerId(Long ownerId);

    boolean existsByOwnerIdAndAddress(Long ownerId, Address address);

    @Query("SELECT l FROM Listing l WHERE l.status = sk.fsa.rental.domain.ListingStatus.ACTIVE " +
           "AND (cast(:city as string) IS NULL OR LOWER(l.address.city) LIKE LOWER(CONCAT('%', cast(:city as string), '%'))) " +
           "AND (:listingType IS NULL OR l.listingType = :listingType) " +
           "AND (:propertyType IS NULL OR l.features.propertyType = :propertyType)")
    Page<Listing> search(@Param("city") String city,
                         @Param("listingType") ListingType listingType,
                         @Param("propertyType") PropertyType propertyType,
                         Pageable pageable);
}

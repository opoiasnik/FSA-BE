package sk.fsa.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sk.fsa.rental.domain.Listing;

import java.util.List;

interface ListingSpringDataRepository extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {

    List<Listing> findByOwnerId(Long ownerId);

    boolean existsByOwnerIdAndAddressStreetAndAddressCityAndAddressPostalCodeAndAddressCountry(
            Long ownerId, String addressStreet, String addressCity,
            String addressPostalCode, String addressCountry);
}

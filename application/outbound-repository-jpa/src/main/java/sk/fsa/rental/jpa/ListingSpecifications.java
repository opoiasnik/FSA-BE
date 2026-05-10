package sk.fsa.rental.jpa;

import org.springframework.data.jpa.domain.Specification;
import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.ListingSearchFilters;
import sk.fsa.rental.domain.ListingStatus;

final class ListingSpecifications {

    private ListingSpecifications() {}

    static Specification<Listing> from(ListingSearchFilters f) {
        return isActive()
                .and(cityLike(f.city()))
                .and(listingTypeEq(f))
                .and(propertyTypeEq(f))
                .and(priceMin(f.priceMin()))
                .and(priceMax(f.priceMax()))
                .and(roomCountEq(f.roomCount()))
                .and(areaMin(f.areaMin()))
                .and(areaMax(f.areaMax()))
                .and(furnishedEq(f.furnished()))
                .and(parkingEq(f.parkingAvailable()))
                .and(balconyEq(f.balcony()))
                .and(petsEq(f.petsAllowed()))
                .and(energyClassEq(f.energyClass()));
    }

    private static Specification<Listing> isActive() {
        return (root, query, cb) -> cb.equal(root.get("status"), ListingStatus.ACTIVE);
    }

    private static Specification<Listing> cityLike(String city) {
        return (root, query, cb) -> city == null ? cb.conjunction()
                : cb.like(cb.lower(root.get("address").get("city")), "%" + city.toLowerCase() + "%");
    }

    private static Specification<Listing> listingTypeEq(ListingSearchFilters f) {
        return (root, query, cb) -> f.listingType() == null ? cb.conjunction()
                : cb.equal(root.get("listingType"), f.listingType());
    }

    private static Specification<Listing> propertyTypeEq(ListingSearchFilters f) {
        return (root, query, cb) -> f.propertyType() == null ? cb.conjunction()
                : cb.equal(root.get("features").get("propertyType"), f.propertyType());
    }

    private static Specification<Listing> priceMin(Double min) {
        return (root, query, cb) -> min == null ? cb.conjunction()
                : cb.greaterThanOrEqualTo(root.get("price").get("amount"), min);
    }

    private static Specification<Listing> priceMax(Double max) {
        return (root, query, cb) -> max == null ? cb.conjunction()
                : cb.lessThanOrEqualTo(root.get("price").get("amount"), max);
    }

    private static Specification<Listing> roomCountEq(Integer roomCount) {
        return (root, query, cb) -> roomCount == null ? cb.conjunction()
                : cb.equal(root.get("features").get("roomCount"), roomCount);
    }

    private static Specification<Listing> areaMin(Double min) {
        return (root, query, cb) -> min == null ? cb.conjunction()
                : cb.greaterThanOrEqualTo(root.get("features").get("area"), min);
    }

    private static Specification<Listing> areaMax(Double max) {
        return (root, query, cb) -> max == null ? cb.conjunction()
                : cb.lessThanOrEqualTo(root.get("features").get("area"), max);
    }

    private static Specification<Listing> furnishedEq(Boolean furnished) {
        return (root, query, cb) -> furnished == null ? cb.conjunction()
                : cb.equal(root.get("features").get("furnished"), furnished);
    }

    private static Specification<Listing> parkingEq(Boolean parking) {
        return (root, query, cb) -> parking == null ? cb.conjunction()
                : cb.equal(root.get("features").get("parkingAvailable"), parking);
    }

    private static Specification<Listing> balconyEq(Boolean balcony) {
        return (root, query, cb) -> balcony == null ? cb.conjunction()
                : cb.equal(root.get("features").get("balcony"), balcony);
    }

    private static Specification<Listing> petsEq(Boolean pets) {
        return (root, query, cb) -> pets == null ? cb.conjunction()
                : cb.equal(root.get("features").get("petsAllowed"), pets);
    }

    private static Specification<Listing> energyClassEq(String energyClass) {
        return (root, query, cb) -> energyClass == null ? cb.conjunction()
                : cb.equal(root.get("features").get("energyClass"), energyClass);
    }
}

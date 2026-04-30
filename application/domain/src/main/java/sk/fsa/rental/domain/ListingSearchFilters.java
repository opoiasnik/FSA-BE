package sk.fsa.rental.domain;

public record ListingSearchFilters(
        String city,
        ListingType listingType,
        PropertyType propertyType,
        int page,
        int size
) {
}

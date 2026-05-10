package sk.fsa.rental.domain;

public record ListingSearchFilters(
        String city,
        ListingType listingType,
        PropertyType propertyType,
        Double priceMin,
        Double priceMax,
        Integer roomCount,
        Double areaMin,
        Double areaMax,
        Boolean furnished,
        Boolean parkingAvailable,
        Boolean balcony,
        Boolean petsAllowed,
        String energyClass,
        SortBy sortBy,
        int page,
        int size
) {
}

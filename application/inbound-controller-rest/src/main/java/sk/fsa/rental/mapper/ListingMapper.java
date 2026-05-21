package sk.fsa.rental.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.ListingSearchFilters;
import sk.fsa.rental.domain.ListingSearchResult;
import sk.fsa.rental.domain.ListingType;
import sk.fsa.rental.domain.Photo;
import sk.fsa.rental.domain.Price;
import sk.fsa.rental.domain.PropertyFeatures;
import sk.fsa.rental.domain.PropertyType;
import sk.fsa.rental.domain.SortBy;
import sk.fsa.rental.rest.dto.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface ListingMapper {

    default OffsetDateTime map(Date date) {
        if (date == null) {
            return null;
        }
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneOffset.UTC);
    }

    default Listing toDomain(CreateListingRequestDto dto) {
        if (dto == null) {
            return null;
        }
        return new Listing(
                dto.getTitle(),
                dto.getDescription(),
                dto.getListingType() != null ? ListingType.valueOf(dto.getListingType().name()) : null,
                toDomain(dto.getAddress()),
                toDomain(dto.getPrice()),
                toDomain(dto.getFeatures()));
    }

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner", target = "owner")
    ListingResponseDto toDto(Listing listing);

    @Mapping(source = "address.city", target = "city")
    @Mapping(target = "coverPhoto", expression = "java(toCoverPhoto(listing))")
    ListingSummaryDto toSummary(Listing listing);

    @Mapping(target = "contentUrl", expression = "java(toPhotoContentUrl(photo))")
    PhotoResponseDto toDto(Photo photo);

    default PhotoResponseDto toCoverPhoto(Listing listing) {
        if (listing == null || listing.getPhotos().isEmpty()) {
            return null;
        }
        return listing.getPhotos().stream()
                .filter(photo -> photo.getPosition() != null)
                .min(java.util.Comparator.comparing(Photo::getPosition))
                .map(this::toPublicCoverDto)
                .orElseGet(() -> toPublicCoverDto(listing.getPhotos().getFirst()));
    }

    default String toPhotoContentUrl(Photo photo) {
        if (photo == null || photo.getId() == null) {
            return null;
        }
        return "/api/photos/" + photo.getId() + "/content";
    }

    private PhotoResponseDto toPublicCoverDto(Photo photo) {
        PhotoResponseDto dto = toDto(photo);
        if (photo != null && photo.getId() != null) {
            dto.setContentUrl("/api/photos/" + photo.getId() + "/cover-content");
        }
        return dto;
    }

    default ListingSearchFilters toFilters(
            String city, ListingTypeDto listingType, PropertyTypeDto propertyType,
            Double priceMin, Double priceMax, Integer roomCount,
            Double areaMin, Double areaMax, Boolean furnished,
            Boolean parkingAvailable, Boolean balcony, Boolean petsAllowed,
            String energyClass, String sortBy, Integer page, Integer size) {
        return new ListingSearchFilters(
                city,
                listingType != null ? ListingType.valueOf(listingType.name()) : null,
                propertyType != null ? PropertyType.valueOf(propertyType.name()) : null,
                priceMin, priceMax, roomCount, areaMin, areaMax,
                furnished, parkingAvailable, balcony, petsAllowed,
                energyClass, SortBy.fromString(sortBy),
                page != null ? page : 0,
                size != null ? size : 10);
    }

    default ListingSearchResponseDto toDto(ListingSearchResult result) {
        return new ListingSearchResponseDto()
                .content(result.content().stream().map(this::toDto).toList())
                .pagination(new PaginationResponseDto()
                        .page(result.page())
                        .size(result.size())
                        .totalElements(result.totalElements())
                        .totalPages(result.totalPages()));
    }

    AddressResponseDto toDto(sk.fsa.rental.domain.Address address);

    default sk.fsa.rental.domain.Address toDomain(AddressRequestDto dto) {
        if (dto == null) {
            return null;
        }
        return new sk.fsa.rental.domain.Address(
                dto.getStreet(),
                dto.getCity(),
                dto.getPostalCode(),
                dto.getCountry(),
                dto.getDistrict(),
                dto.getRegion(),
                dto.getLat(),
                dto.getLng());
    }

    PriceResponseDto toDto(sk.fsa.rental.domain.Price price);

    default sk.fsa.rental.domain.Price toDomain(PriceRequestDto dto) {
        if (dto == null) {
            return null;
        }
        return new Price(
                dto.getAmount() != null ? BigDecimal.valueOf(dto.getAmount()) : null,
                dto.getCurrency());
    }

    PropertyFeaturesResponseDto toDto(sk.fsa.rental.domain.PropertyFeatures features);

    default sk.fsa.rental.domain.PropertyFeatures toDomain(PropertyFeaturesRequestDto dto) {
        if (dto == null) {
            return null;
        }
        return new PropertyFeatures(
                dto.getPropertyType() != null ? PropertyType.valueOf(dto.getPropertyType().name()) : null,
                dto.getArea(),
                dto.getRoomCount(),
                dto.getFloor(),
                dto.getFurnished(),
                dto.getParkingAvailable(),
                dto.getBalcony(),
                dto.getElevator(),
                dto.getPetsAllowed(),
                dto.getEnergyClass(),
                dto.getYearBuilt());
    }
}

package sk.fsa.rental.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.rest.dto.*;

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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "photos", ignore = true)
    Listing toDomain(CreateListingRequestDto dto);

    @Mapping(source = "owner.id", target = "ownerId")
    ListingResponseDto toDto(Listing listing);

    AddressResponseDto toDto(sk.fsa.rental.domain.Address address);

    sk.fsa.rental.domain.Address toDomain(AddressRequestDto dto);

    PriceResponseDto toDto(sk.fsa.rental.domain.Price price);

    sk.fsa.rental.domain.Price toDomain(PriceRequestDto dto);

    PropertyFeaturesResponseDto toDto(sk.fsa.rental.domain.PropertyFeatures features);

    sk.fsa.rental.domain.PropertyFeatures toDomain(PropertyFeaturesRequestDto dto);
}

package sk.fsa.rental.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sk.fsa.rental.domain.ViewingRequest;
import sk.fsa.rental.rest.dto.CreateViewingRequestDto;
import sk.fsa.rental.rest.dto.ViewingRequestResponseDto;

import java.time.OffsetDateTime;
import java.util.Date;

@Mapper(componentModel = "spring", uses = ListingMapper.class)
public interface ViewingRequestMapper {

    default Date toDate(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }
        return Date.from(offsetDateTime.toInstant());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "requester", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "listing", ignore = true)
    ViewingRequest toDomain(CreateViewingRequestDto dto);

    @Mapping(source = "listing", target = "listing")
    @Mapping(source = "requester", target = "requester")
    @Mapping(source = "owner", target = "owner")
    ViewingRequestResponseDto toDto(ViewingRequest viewingRequest);
}

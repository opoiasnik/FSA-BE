package sk.fsa.rental.mapper;

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

    default ViewingRequest toDomain(CreateViewingRequestDto dto) {
        if (dto == null) {
            return null;
        }
        return new ViewingRequest(toDate(dto.getRequestedDate()), dto.getNote());
    }

    @Mapping(source = "listing", target = "listing")
    @Mapping(source = "requester", target = "requester")
    @Mapping(source = "owner", target = "owner")
    ViewingRequestResponseDto toDto(ViewingRequest viewingRequest);
}

package sk.fsa.rental.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sk.fsa.rental.domain.Favorite;
import sk.fsa.rental.rest.dto.FavoriteResponseDto;

@Mapper(componentModel = "spring", uses = ListingMapper.class)
public interface FavoriteMapper {

    @Mapping(source = "listing", target = "listing")
    FavoriteResponseDto toDto(Favorite favorite);
}

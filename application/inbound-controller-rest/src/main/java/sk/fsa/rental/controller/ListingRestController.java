package sk.fsa.rental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import sk.fsa.rental.controller.mapper.ListingMapper;
import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.ListingSearchFilters;
import sk.fsa.rental.domain.ListingSearchResult;
import sk.fsa.rental.domain.ListingType;
import sk.fsa.rental.domain.PropertyType;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.ListingFacade;
import sk.fsa.rental.rest.api.ListingApi;
import sk.fsa.rental.rest.dto.CreateListingRequestDto;
import sk.fsa.rental.rest.dto.ListingResponseDto;
import sk.fsa.rental.rest.dto.ListingSearchResponseDto;
import sk.fsa.rental.rest.dto.ListingTypeDto;
import sk.fsa.rental.rest.dto.PaginationResponseDto;
import sk.fsa.rental.rest.dto.PropertyTypeDto;
import sk.fsa.rental.security.CurrentUserDetailService;

import java.util.List;

@RestController
public class ListingRestController implements ListingApi {

    private final ListingFacade listingFacade;
    private final ListingMapper listingMapper;
    private final CurrentUserDetailService currentUserDetailService;

    public ListingRestController(ListingFacade listingFacade, ListingMapper listingMapper,
                                 CurrentUserDetailService currentUserDetailService) {
        this.listingFacade = listingFacade;
        this.listingMapper = listingMapper;
        this.currentUserDetailService = currentUserDetailService;
    }

    @Override
    @Transactional
    public ResponseEntity<ListingResponseDto> createListing(CreateListingRequestDto createListingRequestDto) {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        Listing listing = listingMapper.toDomain(createListingRequestDto);
        Listing created = listingFacade.createListing(listing, currentUser);
        return new ResponseEntity<>(listingMapper.toDto(created), HttpStatus.CREATED);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ListingSearchResponseDto> searchListings(
            String city, ListingTypeDto listingType, Double priceMin, Double priceMax,
            PropertyTypeDto propertyType, Integer page, Integer size) {
        ListingSearchFilters filters = new ListingSearchFilters(
                city,
                listingType != null ? ListingType.valueOf(listingType.name()) : null,
                propertyType != null ? PropertyType.valueOf(propertyType.name()) : null,
                page != null ? page : 0,
                size != null ? size : 10
        );
        ListingSearchResult result = listingFacade.searchListings(filters);
        ListingSearchResponseDto response = new ListingSearchResponseDto()
                .content(result.content().stream().map(listingMapper::toDto).toList())
                .pagination(new PaginationResponseDto()
                        .page(result.page())
                        .size(result.size())
                        .totalElements(result.totalElements())
                        .totalPages(result.totalPages()));
        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ListingResponseDto> getListingById(Long id) {
        Listing listing = listingFacade.getListingById(id);
        return ResponseEntity.ok(listingMapper.toDto(listing));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<ListingResponseDto>> getFeaturedListings() {
        List<ListingResponseDto> response = listingFacade.getFeaturedListings().stream()
                .map(listingMapper::toDto)
                .toList();
        return ResponseEntity.ok(response);
    }
}

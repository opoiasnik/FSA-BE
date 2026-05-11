package sk.fsa.rental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import sk.fsa.rental.controller.mapper.ListingMapper;
import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.ListingType;
import sk.fsa.rental.domain.PropertyType;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.ListingFacade;
import sk.fsa.rental.rest.api.ListingApi;
import sk.fsa.rental.rest.dto.CreateListingRequestDto;
import sk.fsa.rental.rest.dto.ListingResponseDto;
import sk.fsa.rental.rest.dto.ListingSearchResponseDto;
import sk.fsa.rental.rest.dto.ListingSummaryDto;
import sk.fsa.rental.rest.dto.ListingTypeDto;
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
        Listing created = listingFacade.create(listing, currentUser);
        return new ResponseEntity<>(listingMapper.toDto(created), HttpStatus.CREATED);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ListingSearchResponseDto> searchListings(
            String city, ListingTypeDto listingType, Double priceMin, Double priceMax,
            PropertyTypeDto propertyType, Integer roomCount, Double areaMin, Double areaMax,
            Boolean furnished, Boolean parkingAvailable, Boolean balcony, Boolean petsAllowed,
            String energyClass, String sortBy, Integer page, Integer size) {
        return ResponseEntity.ok(listingMapper.toDto(listingFacade.search(listingMapper.toFilters(
                city, listingType, propertyType, priceMin, priceMax, roomCount,
                areaMin, areaMax, furnished, parkingAvailable, balcony, petsAllowed,
                energyClass, sortBy, page, size))));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ListingResponseDto> getListingById(Long id) {
        return ResponseEntity.ok(listingMapper.toDto(listingFacade.getById(id)));
    }

    @Override
    @Transactional
    public ResponseEntity<List<ListingResponseDto>> getMyListings() {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        return ResponseEntity.ok(listingFacade.getByOwner(currentUser.getId()).stream()
                .map(listingMapper::toDto)
                .toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<ListingSummaryDto>> getFeaturedListings(
            String city, ListingTypeDto listingType, PropertyTypeDto propertyType) {
        return ResponseEntity.ok(listingFacade.getFeatured(
                        city,
                        listingType != null ? ListingType.valueOf(listingType.name()) : null,
                        propertyType != null ? PropertyType.valueOf(propertyType.name()) : null)
                .stream()
                .map(listingMapper::toSummary)
                .toList());
    }
}

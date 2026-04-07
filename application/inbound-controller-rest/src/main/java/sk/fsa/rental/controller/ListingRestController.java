package sk.fsa.rental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import sk.fsa.rental.controller.mapper.ListingMapper;
import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.facade.ListingFacade;
import sk.fsa.rental.rest.api.ListingApi;
import sk.fsa.rental.rest.dto.CreateListingRequestDto;
import sk.fsa.rental.rest.dto.ListingResponseDto;

@RestController
public class ListingRestController implements ListingApi {

    private final ListingFacade listingFacade;
    private final ListingMapper listingMapper;

    public ListingRestController(ListingFacade listingFacade, ListingMapper listingMapper) {
        this.listingFacade = listingFacade;
        this.listingMapper = listingMapper;
    }

    @Override
    @Transactional
    public ResponseEntity<ListingResponseDto> createListing(CreateListingRequestDto createListingRequestDto) {
        String ownerEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Listing listing = listingMapper.toDomain(createListingRequestDto);
        Listing createdListing = listingFacade.createListing(listing, ownerEmail);
        ListingResponseDto response = listingMapper.toDto(createdListing);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ListingResponseDto> getListingById(Long id) {
        Listing listing = listingFacade.getListingById(id);
        ListingResponseDto response = listingMapper.toDto(listing);

        return ResponseEntity.ok(response);
    }
}

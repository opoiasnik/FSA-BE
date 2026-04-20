package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.ListingFactory;
import sk.fsa.rental.domain.ListingStatus;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.ListingFacade;
import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListingService implements ListingFacade {
    private static final int FEATURED_LISTINGS_LIMIT = 4;

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final ListingFactory listingFactory;

    public ListingService(ListingRepository listingRepository, UserRepository userRepository, ListingFactory listingFactory) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
        this.listingFactory = listingFactory;
    }

    @Override
    public Listing createListing(Listing listing, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Owner not found."));

        Listing prepared = listingFactory.createListing(listing, owner);
        return listingRepository.save(prepared);
    }

    @Override
    public Listing updateListing(Long listingId, Listing updatedListing, Long ownerId) {
        Listing existing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Listing not found."));

        if (!existing.getOwner().getId().equals(ownerId)) {
            throw new RentalException(RentalException.Type.FORBIDDEN,
                    "Only the owner can update this listing.");
        }

        existing.setTitle(updatedListing.getTitle());
        existing.setDescription(updatedListing.getDescription());
        existing.setListingType(updatedListing.getListingType());
        existing.setAddress(updatedListing.getAddress());
        existing.setPrice(updatedListing.getPrice());
        existing.setFeatures(updatedListing.getFeatures());

        existing.validateForUpdate();

        return listingRepository.save(existing);
    }

    @Override
    public void deleteListing(Long listingId, Long ownerId) {
        Listing existing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Listing not found."));

        if (!existing.getOwner().getId().equals(ownerId)) {
            throw new RentalException(RentalException.Type.FORBIDDEN,
                    "Only the owner can delete this listing.");
        }

        listingRepository.deleteById(listingId);
    }

    @Override
    public Listing activateListing(Long listingId, Long ownerId) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Listing not found."));

        if (!listing.getOwner().getId().equals(ownerId)) {
            throw new RentalException(RentalException.Type.FORBIDDEN,
                    "Only the owner can activate this listing.");
        }

        listing.activate();
        return listingRepository.save(listing);
    }

    @Override
    public Listing getListingById(Long id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Listing not found."));
    }

    @Override
    public List<Listing> getFeaturedListings() {
        List<Listing> activeListings = listingRepository.findByStatus(ListingStatus.ACTIVE);
        if (activeListings.isEmpty()) {
            return List.of();
        }

        List<Listing> shuffledListings = new ArrayList<>(activeListings);
        Collections.shuffle(shuffledListings);

        int resultSize = Math.min(FEATURED_LISTINGS_LIMIT, shuffledListings.size());
        return List.copyOf(shuffledListings.subList(0, resultSize));
    }
}

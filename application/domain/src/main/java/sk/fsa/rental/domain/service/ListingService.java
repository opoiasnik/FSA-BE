package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.ListingFacade;
import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.repository.UserRepository;

public class ListingService implements ListingFacade {
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public ListingService(ListingRepository listingRepository, UserRepository userRepository) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Listing createListing(Listing listing, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Owner not found."));

        listing.setOwner(owner);
        listing.validateForCreation();

        return listingRepository.save(listing);
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
}

package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.ListingFactory;
import sk.fsa.rental.domain.ListingSearchFilters;
import sk.fsa.rental.domain.ListingSearchResult;
import sk.fsa.rental.domain.ListingType;
import sk.fsa.rental.domain.ListingViewEvent;
import sk.fsa.rental.domain.Photo;
import sk.fsa.rental.domain.PhotoFactory;
import sk.fsa.rental.domain.PropertyType;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.SortBy;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.ListingFacade;
import sk.fsa.rental.domain.predicate.listing.IsOwnedByPredicate;
import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.repository.ListingViewEventRepository;

import java.util.List;


public class ListingService implements ListingFacade {

    private static final int FEATURED_LISTINGS_LIMIT = 5;

    private final ListingRepository listingRepository;
    private final ListingFactory listingFactory;
    private final ListingViewEventRepository listingViewEventRepository;
    private final PhotoFactory photoFactory;

    public ListingService(ListingRepository listingRepository, ListingFactory listingFactory,
                          ListingViewEventRepository listingViewEventRepository,
                          PhotoFactory photoFactory) {
        this.listingRepository = listingRepository;
        this.listingFactory = listingFactory;
        this.listingViewEventRepository = listingViewEventRepository;
        this.photoFactory = photoFactory;
    }

    @Override
    public Listing create(Listing listing, User owner) {
        Listing prepared = listingFactory.create(listing, owner);
        return listingRepository.save(prepared);
    }

    @Override
    public Listing update(Long listingId, Listing updatedListing, User editor) {
        Listing existing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Listing not found."));

        existing.update(
                editor,
                updatedListing.getTitle(),
                updatedListing.getDescription(),
                updatedListing.getListingType(),
                updatedListing.getAddress(),
                updatedListing.getPrice(),
                updatedListing.getFeatures()
        );

        return listingRepository.save(existing);
    }

    @Override
    public void delete(Long listingId, User editor) {
        Listing existing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Listing not found."));

        existing.delete(editor);
        listingRepository.deleteById(listingId);
    }

    @Override
    public Listing activate(Long listingId, User editor) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Listing not found."));

        listing.activate(editor);
        return listingRepository.save(listing);
    }

    @Override
    public Listing getById(Long id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Listing not found."));
    }

    @Override
    public ListingSearchResult search(ListingSearchFilters filters) {
        return listingRepository.search(filters);
    }

    @Override
    public List<Listing> getFeatured(String city, ListingType listingType, PropertyType propertyType) {
        ListingSearchFilters filters = new ListingSearchFilters(
                city, listingType, propertyType,
                null, null, null, null, null, null, null, null, null, null,
                SortBy.NEWEST, 0, FEATURED_LISTINGS_LIMIT);
        return listingRepository.findTopViewed(filters);
    }

    @Override
    public List<Listing> getByOwner(Long ownerId) {
        return listingRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Photo> getPhotos(Long listingId, User requester) {
        List<Photo> photos = getById(listingId).getPhotos();
        boolean canViewAll = photos.stream().allMatch(photo -> photo.canBeViewedBy(requester));
        require(canViewAll, RentalException.Type.FORBIDDEN, "Photos are not available for this user.");
        return photos;
    }

    @Override
    public long countViews(Long listingId) {
        getById(listingId);
        return listingViewEventRepository.countByListingId(listingId);
    }

    @Override
    public void recordView(Long listingId, Long viewerId) {
        Listing listing = getById(listingId);
        Long ownerId = listing.getOwner().getId();
        if (ownerId.equals(viewerId)) {
            return; // owners don't inflate their own stats
        }
        listingViewEventRepository.save(new ListingViewEvent(listingId, ownerId));
    }

    @Override
    public Photo addPhoto(Long listingId, User owner, byte[] data, String contentType,
                          String originalFilename, String altText) {
        Listing listing = getById(listingId);
        require(IsOwnedByPredicate.INSTANCE.test(listing.getOwner(), owner),
                RentalException.Type.FORBIDDEN, "Only the owner can add photos to this listing.");
        Photo photo = photoFactory.create(data, contentType, originalFilename, altText, listing.getPhotos().size());
        listing.addPhoto(photo);
        Listing saved = listingRepository.save(listing);
        return saved.getPhotos().stream()
                .filter(savedPhoto -> savedPhoto.getPosition() != null
                        && savedPhoto.getPosition().equals(photo.getPosition()))
                .reduce((first, second) -> second)
                .orElse(photo);
    }

    private void require(boolean valid, RentalException.Type type, String message) {
        if (!valid) {
            throw new RentalException(type, message);
        }
    }

}

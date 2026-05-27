package sk.fsa.rental.domain.facade;

import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.ListingSearchFilters;
import sk.fsa.rental.domain.ListingSearchResult;
import sk.fsa.rental.domain.ListingType;
import sk.fsa.rental.domain.Photo;
import sk.fsa.rental.domain.PropertyType;
import sk.fsa.rental.domain.User;

import java.util.List;

public interface ListingFacade {

    Listing create(Listing listing, User owner);

    Listing update(Long listingId, Listing updatedListing, User editor, List<Long> photoIdsToKeep);

    void delete(Long listingId, User editor);

    Listing activate(Long listingId, User editor);

    Listing deactivate(Long listingId, User editor);

    Listing getById(Long id);

    Listing getVisibleById(Long id, User requester);

    List<Listing> getFeatured(String city, ListingType listingType, PropertyType propertyType);

    List<Listing> getByOwner(Long ownerId);

    ListingSearchResult search(ListingSearchFilters filters);

    List<Photo> getPhotos(Long listingId, User requester);

    long countViews(Long listingId);

    Photo addPhoto(Long listingId, User owner, byte[] data, String contentType,
                   String originalFilename, String altText);

    void recordView(Long listingId, Long viewerId);
}

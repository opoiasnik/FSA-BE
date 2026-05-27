package sk.fsa.rental.domain;

import sk.fsa.rental.domain.predicate.listing.HasRequiredDescriptionPredicate;
import sk.fsa.rental.domain.predicate.listing.HasRequiredListingTypePredicate;
import sk.fsa.rental.domain.predicate.listing.HasRequiredOwnerPredicate;
import sk.fsa.rental.domain.predicate.listing.HasRequiredTitlePredicate;
import sk.fsa.rental.domain.predicate.listing.IsListingActivePredicate;
import sk.fsa.rental.domain.predicate.listing.IsListingInactivePredicate;
import sk.fsa.rental.domain.predicate.listing.IsOwnedByPredicate;
import sk.fsa.rental.domain.predicate.user.IsOwnerRolePredicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Listing {
    private Long id;
    private String title;
    private String description;
    private ListingType listingType;
    private ListingStatus status;
    private Date createdAt;
    private User owner;
    private Address address;
    private Price price;
    private PropertyFeatures features;
    private List<Photo> photos;

    public Listing() {
        this.photos = new ArrayList<>();
        this.status = ListingStatus.ACTIVE;
        this.createdAt = new Date();
    }

    public Listing(String title, String description, ListingType listingType,
                   Address address, Price price, PropertyFeatures features) {
        this();
        this.title = title;
        this.description = description;
        this.listingType = listingType;
        this.address = address;
        this.price = price;
        this.features = features;
    }

    public void validateForCreation() {
        requireField(HasRequiredTitlePredicate.INSTANCE.test(title),
                "title", "Listing title is required.");
        requireField(HasRequiredDescriptionPredicate.INSTANCE.test(description),
                "description", "Listing description is required.");
        requireField(HasRequiredListingTypePredicate.INSTANCE.test(listingType),
                "listingType", "Listing type (RENT/SALE) is required.");
        require(HasRequiredOwnerPredicate.INSTANCE.test(this),
                RentalException.Type.VALIDATION, "Listing must have an owner.");
        require(IsOwnerRolePredicate.INSTANCE.test(owner),
                RentalException.Type.FORBIDDEN, "Only users with OWNER role can create listings.");
        requireField(address != null, "address", "Address is required.");
        address.validate();
        requireField(price != null, "price", "Price is required.");
        price.validate();
        requireField(features != null, "features", "Property features (including property type) are required.");
        features.validate();
    }

    public void update(User editor, String title, String description, ListingType listingType,
                       Address address, Price price, PropertyFeatures features) {
        require(IsOwnedByPredicate.INSTANCE.test(owner, editor),
                RentalException.Type.FORBIDDEN, "Only the owner can update this listing.");
        this.title = title;
        this.description = description;
        this.listingType = listingType;
        this.address = address;
        this.price = price;
        this.features = features;
        validateForCreation();
    }

    public void activate(User editor) {
        require(IsOwnedByPredicate.INSTANCE.test(owner, editor),
                RentalException.Type.FORBIDDEN, "Only the owner can activate this listing.");
        require(IsListingInactivePredicate.INSTANCE.test(this),
                RentalException.Type.VALIDATION, "Listing is already active.");
        this.status = ListingStatus.ACTIVE;
    }

    public void deactivate(User editor) {
        require(IsOwnedByPredicate.INSTANCE.test(owner, editor),
                RentalException.Type.FORBIDDEN, "Only the owner can deactivate this listing.");
        require(IsListingActivePredicate.INSTANCE.test(this),
                RentalException.Type.VALIDATION, "Listing is already inactive.");
        this.status = ListingStatus.INACTIVE;
    }

    public void delete(User editor) {
        require(IsOwnedByPredicate.INSTANCE.test(owner, editor),
                RentalException.Type.FORBIDDEN, "Only the owner can delete this listing.");
        this.status = ListingStatus.DELETED;
    }

    public void addPhoto(Photo photo) {
        require(photo != null,
                RentalException.Type.VALIDATION, "Photo cannot be null.");
        photo.setListing(this);
        photos.add(photo);
    }

    /**
     * Removes photos whose IDs are not in {@code keepIds} and re-normalises
     * positions so that position 0 is always the cover photo.
     * Passing an empty list removes all photos.
     * Passing {@code null} is a no-op (nothing changes).
     */
    public void removePhotosNotIn(List<Long> keepIds) {
        if (keepIds == null) {
            return;
        }
        Set<Long> keep = new java.util.HashSet<>(keepIds);
        photos.removeIf(photo -> photo.getId() != null && !keep.contains(photo.getId()));
        for (int i = 0; i < photos.size(); i++) {
            photos.get(i).setPosition(i);
        }
    }

    private void require(boolean valid, RentalException.Type type, String message) {
        if (!valid) {
            throw new RentalException(type, message);
        }
    }

    private void requireField(boolean valid, String field, String message) {
        if (!valid) {
            throw new RentalException(RentalException.Type.VALIDATION, message, field);
        }
    }

    public Long getId() { return id; }
    void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    void setDescription(String description) { this.description = description; }

    public ListingType getListingType() { return listingType; }
    void setListingType(ListingType listingType) { this.listingType = listingType; }

    public ListingStatus getStatus() { return status; }

    public Date getCreatedAt() { return createdAt; }

    public User getOwner() { return owner; }
    void setOwner(User owner) { this.owner = owner; }

    public Address getAddress() { return address; }
    void setAddress(Address address) { this.address = address; }

    public Price getPrice() { return price; }
    void setPrice(Price price) { this.price = price; }

    public PropertyFeatures getFeatures() { return features; }
    void setFeatures(PropertyFeatures features) { this.features = features; }

    public List<Photo> getPhotos() { return Collections.unmodifiableList(photos); }
}

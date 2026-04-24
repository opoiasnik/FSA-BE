package sk.fsa.rental.domain;

import sk.fsa.rental.domain.predicate.listing.HasRequiredDescriptionPredicate;
import sk.fsa.rental.domain.predicate.listing.HasRequiredListingTypePredicate;
import sk.fsa.rental.domain.predicate.listing.HasRequiredOwnerPredicate;
import sk.fsa.rental.domain.predicate.listing.HasRequiredTitlePredicate;
import sk.fsa.rental.domain.predicate.listing.IsListingActivePredicate;
import sk.fsa.rental.domain.predicate.listing.IsListingInactivePredicate;
import sk.fsa.rental.domain.predicate.user.IsOwnerRolePredicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
        this.status = ListingStatus.INACTIVE;
        this.createdAt = new Date();
    }

    public void validateForCreation() {
        if (!HasRequiredTitlePredicate.INSTANCE.test(title))
            throw new RentalException(RentalException.Type.VALIDATION, "Listing title is required.");
        if (!HasRequiredDescriptionPredicate.INSTANCE.test(description))
            throw new RentalException(RentalException.Type.VALIDATION, "Listing description is required.");
        if (!HasRequiredListingTypePredicate.INSTANCE.test(listingType))
            throw new RentalException(RentalException.Type.VALIDATION, "Listing type (RENT/SALE) is required.");
        if (!HasRequiredOwnerPredicate.INSTANCE.test(this))
            throw new RentalException(RentalException.Type.VALIDATION, "Listing must have an owner.");
        if (!IsOwnerRolePredicate.INSTANCE.test(owner))
            throw new RentalException(RentalException.Type.FORBIDDEN, "Only users with OWNER role can create listings.");
        if (address == null) throw new RentalException(RentalException.Type.VALIDATION, "Address is required.");
        address.validate();
        if (price == null) throw new RentalException(RentalException.Type.VALIDATION, "Price is required.");
        price.validate();
        if (features == null) throw new RentalException(RentalException.Type.VALIDATION, "Property features (including property type) are required.");
        features.validate();
    }

    public void validateForUpdate() {
        validateForCreation();
    }

    public void activate() {
        if (!IsListingInactivePredicate.INSTANCE.test(this))
            throw new RentalException(RentalException.Type.VALIDATION, "Listing is already active.");
        this.status = ListingStatus.ACTIVE;
    }

    public void deactivate() {
        if (!IsListingActivePredicate.INSTANCE.test(this))
            throw new RentalException(RentalException.Type.VALIDATION, "Listing is already inactive.");
        this.status = ListingStatus.INACTIVE;
    }

    public void addPhoto(Photo photo) {
        if (photo == null) throw new RentalException(RentalException.Type.VALIDATION, "Photo cannot be null.");
        photo.setListing(this);
        photos.add(photo);
    }

    public void removePhoto(Long photoId) {
        photos.removeIf(p -> p.getId().equals(photoId));
    }

    public void updatePrice(Price newPrice) {
        if (newPrice == null) throw new RentalException(RentalException.Type.VALIDATION, "New price cannot be null.");
        newPrice.validate();
        this.price = newPrice;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ListingType getListingType() { return listingType; }
    public void setListingType(ListingType listingType) { this.listingType = listingType; }

    public ListingStatus getStatus() { return status; }

    public Date getCreatedAt() { return createdAt; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public Price getPrice() { return price; }
    public void setPrice(Price price) { this.price = price; }

    public PropertyFeatures getFeatures() { return features; }
    public void setFeatures(PropertyFeatures features) { this.features = features; }

    public List<Photo> getPhotos() { return Collections.unmodifiableList(photos); }
}

package sk.fsa.rental.domain;

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
        require(title != null && !title.trim().isEmpty(),
                RentalException.Type.VALIDATION,
                "Listing title is required.");
        require(description != null && !description.trim().isEmpty(),
                RentalException.Type.VALIDATION,
                "Listing description is required.");
        require(listingType != null,
                RentalException.Type.VALIDATION,
                "Listing type (RENT/SALE) is required.");
        require(owner != null,
                RentalException.Type.VALIDATION,
                "Listing must have an owner.");
        require(owner.isOwner(),
                RentalException.Type.FORBIDDEN,
                "Only users with OWNER role can create listings.");
        require(address != null,
                RentalException.Type.VALIDATION,
                "Address is required.");
        address.validate();
        require(price != null,
                RentalException.Type.VALIDATION,
                "Price is required.");
        price.validate();
        require(features != null,
                RentalException.Type.VALIDATION,
                "Property features (including property type) are required.");
        features.validate();
    }

    public void validateForUpdate() {
        validateForCreation();
    }

    public void activate() {
        require(status == ListingStatus.INACTIVE,
                RentalException.Type.VALIDATION,
                "Listing is already active.");
        this.status = ListingStatus.ACTIVE;
    }

    public void deactivate() {
        require(status == ListingStatus.ACTIVE,
                RentalException.Type.VALIDATION,
                "Listing is already inactive.");
        this.status = ListingStatus.INACTIVE;
    }

    public void addPhoto(Photo photo) {
        require(photo != null, RentalException.Type.VALIDATION, "Photo cannot be null.");
        photo.setListing(this);
        photos.add(photo);
    }

    public void removePhoto(Long photoId) {
        photos.removeIf(p -> p.getId().equals(photoId));
    }

    public void updatePrice(Price newPrice) {
        require(newPrice != null, RentalException.Type.VALIDATION, "New price cannot be null.");
        newPrice.validate();
        this.price = newPrice;
    }

    private void require(boolean condition, RentalException.Type type, String message) {
        if (!condition) {
            throw new RentalException(type, message);
        }
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

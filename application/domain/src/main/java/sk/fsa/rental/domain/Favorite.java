package sk.fsa.rental.domain;

import sk.fsa.rental.domain.predicate.favorite.HasRequiredFieldsPredicate;
import sk.fsa.rental.domain.predicate.favorite.IsNotOwnListingPredicate;
import sk.fsa.rental.domain.predicate.listing.IsListingActivePredicate;

import java.util.Date;

public class Favorite {
    private Long id;
    private User user;
    private Listing listing;
    private Date createdAt;

    public Favorite() {
        this.createdAt = new Date();
    }

    public void validateForCreation() {
        require(HasRequiredFieldsPredicate.INSTANCE.test(this),
                RentalException.Type.VALIDATION, "Favorite must have a user and a listing.");
        require(IsListingActivePredicate.INSTANCE.test(listing),
                RentalException.Type.VALIDATION, "Cannot favorite an inactive listing.");
        require(IsNotOwnListingPredicate.INSTANCE.test(this),
                RentalException.Type.VALIDATION, "Cannot add your own listing to favorites.");
    }

    private void require(boolean valid, RentalException.Type type, String message) {
        if (!valid) {
            throw new RentalException(type, message);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}

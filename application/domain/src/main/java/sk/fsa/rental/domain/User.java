package sk.fsa.rental.domain;

import sk.fsa.rental.domain.predicate.user.HasRequiredNamePredicate;
import sk.fsa.rental.domain.predicate.user.HasRequiredPasswordPredicate;
import sk.fsa.rental.domain.predicate.user.HasRequiredRolePredicate;
import sk.fsa.rental.domain.predicate.user.HasValidEmailPredicate;
import sk.fsa.rental.domain.predicate.user.IsOwnerRolePredicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private Long id;
    private String name;
    private String email;
    private String passwordHash;
    private UserRole role;
    private List<Listing> ownedListings;
    private List<Favorite> favorites;

    public User() {
        this.ownedListings = new ArrayList<>();
        this.favorites = new ArrayList<>();
    }

    public User(String name, String email, String passwordHash, UserRole role) {
        this();
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public void validateForRegistration() {
        if (!HasRequiredNamePredicate.INSTANCE.test(name))
            throw new RentalException(RentalException.Type.VALIDATION, "User name is required.");
        if (!HasValidEmailPredicate.INSTANCE.test(email))
            throw new RentalException(RentalException.Type.VALIDATION, "Valid email is required.");
        if (!HasRequiredPasswordPredicate.INSTANCE.test(passwordHash))
            throw new RentalException(RentalException.Type.VALIDATION, "Password is required.");
        if (!HasRequiredRolePredicate.INSTANCE.test(this))
            throw new RentalException(RentalException.Type.VALIDATION, "User role is required.");
    }

    public boolean isOwner() {
        return IsOwnerRolePredicate.INSTANCE.test(this);
    }

    public void addToFavorites(Listing listing) {
        if (listing == null)
            throw new RentalException(RentalException.Type.VALIDATION, "Listing cannot be null.");

        boolean alreadyFavorite = favorites.stream()
                .anyMatch(f -> f.getListing().equals(listing));

        if (alreadyFavorite)
            throw new RentalException(RentalException.Type.VALIDATION, "Listing is already in favorites.");

        Favorite favorite = new Favorite();
        favorite.setUser(this);
        favorite.setListing(listing);
        favorites.add(favorite);
    }

    public void removeFromFavorites(Long listingId) {
        favorites.removeIf(f -> f.getListing().getId().equals(listingId));
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Listing> getOwnedListings() {
        return Collections.unmodifiableList(ownedListings);
    }

    public void setOwnedListings(List<Listing> ownedListings) {
        this.ownedListings = ownedListings;
    }

    public List<Favorite> getFavorites() {
        return Collections.unmodifiableList(favorites);
    }
}

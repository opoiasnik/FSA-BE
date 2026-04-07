package sk.fsa.rental.domain;

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
        require(name != null && !name.trim().isEmpty(),
                RentalException.Type.VALIDATION,
                "User name is required.");
        require(email != null && email.contains("@"),
                RentalException.Type.VALIDATION,
                "Valid email is required.");
        require(passwordHash != null && !passwordHash.isEmpty(),
                RentalException.Type.VALIDATION,
                "Password is required.");
        require(role != null,
                RentalException.Type.VALIDATION,
                "User role is required.");
    }

    public boolean isOwner() {
        return UserRole.OWNER.equals(role);
    }

    public void addToFavorites(Listing listing) {
        require(listing != null,
                RentalException.Type.VALIDATION,
                "Listing cannot be null.");

        boolean alreadyFavorite = favorites.stream()
                .anyMatch(f -> f.getListing().equals(listing));

        require(!alreadyFavorite,
                RentalException.Type.VALIDATION,
                "Listing is already in favorites.");

        Favorite favorite = new Favorite();
        favorite.setUser(this);
        favorite.setListing(listing);
        favorites.add(favorite);
    }

    public void removeFromFavorites(Long listingId) {
        favorites.removeIf(f -> f.getListing().getId().equals(listingId));
    }

    private void require(boolean condition, RentalException.Type type, String message) {
        if (!condition) {
            throw new RentalException(type, message);
        }
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

package sk.fsa.rental.domain;

import sk.fsa.rental.domain.predicate.user.IsOwnerRolePredicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private Long id;
    private String keycloakId;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String bio;
    private UserRole role;
    private Photo avatarPhoto;
    private List<Listing> ownedListings;
    private List<Favorite> favorites;

    public User() {
        this.ownedListings = new ArrayList<>();
        this.favorites = new ArrayList<>();
    }

    public User(String name, String surname, String email, UserRole role) {
        this();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
    }

    public boolean isOwner() {
        return IsOwnerRolePredicate.INSTANCE.test(this);
    }

    public Favorite addToFavorites(Listing listing) {
        require(listing != null, RentalException.Type.VALIDATION, "Listing cannot be null.");
        boolean alreadyFavorite = favorites.stream()
                .anyMatch(f -> f.getListing().getId().equals(listing.getId()));
        require(!alreadyFavorite, RentalException.Type.VALIDATION, "Listing is already in favorites.");
        Favorite favorite = new Favorite();
        favorite.setUser(this);
        favorite.setListing(listing);
        favorite.validateForCreation();
        favorites.add(favorite);
        return favorite;
    }

    public Favorite removeFromFavorites(Long listingId) {
        Favorite toRemove = favorites.stream()
                .filter(f -> f.getListing().getId().equals(listingId))
                .findFirst()
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Favorite not found."));
        favorites.remove(toRemove);
        return toRemove;
    }

    private void require(boolean valid, RentalException.Type type, String message) {
        if (!valid) throw new RentalException(type, message);
    }

    public Photo getAvatarPhoto() { return avatarPhoto; }
    public void setAvatarPhoto(Photo avatarPhoto) { this.avatarPhoto = avatarPhoto; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getKeycloakId() { return keycloakId; }
    public void setKeycloakId(String keycloakId) { this.keycloakId = keycloakId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public List<Listing> getOwnedListings() { return Collections.unmodifiableList(ownedListings); }
    public void setOwnedListings(List<Listing> ownedListings) { this.ownedListings = ownedListings; }

    public List<Favorite> getFavorites() { return Collections.unmodifiableList(favorites); }
}

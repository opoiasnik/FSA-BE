package sk.fsa.rental.domain;

import sk.fsa.rental.domain.predicate.user.HasValidEmailPredicate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User {
    private Long id;
    private String keycloakId;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String bio;
    private boolean emailVerified;
    private String emailVerificationCode;
    private Date emailVerificationExpiresAt;
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

    private void require(boolean valid, RentalException.Type type, String message, String field) {
        if (!valid) throw new RentalException(type, message, field);
    }

    public Photo getAvatarPhoto() { return avatarPhoto; }

    public void assignKeycloakId(String keycloakId) {
        require(keycloakId != null && !keycloakId.isBlank(),
                RentalException.Type.VALIDATION, "Keycloak id is required.");
        this.keycloakId = keycloakId;
    }

    public void updateProfile(String name, String surname, String email, String phone, String bio) {
        require(name != null && !name.isBlank(), RentalException.Type.VALIDATION, "Name is required.");
        require(HasValidEmailPredicate.INSTANCE.test(email), RentalException.Type.VALIDATION, "Valid email is required.");
        if (!Objects.equals(this.email, email)) {
            resetEmailVerification();
        }
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.bio = bio;
    }

    public void updateAvatar(Photo avatarPhoto) {
        require(avatarPhoto != null, RentalException.Type.VALIDATION, "Avatar photo cannot be null.");
        this.avatarPhoto = avatarPhoto;
    }

    public void startEmailVerification(String code, Date expiresAt) {
        require(HasValidEmailPredicate.INSTANCE.test(email),
                RentalException.Type.VALIDATION, "Valid email is required.", "email");
        require(code != null && !code.isBlank(),
                RentalException.Type.VALIDATION, "Verification code is required.", "code");
        require(expiresAt != null,
                RentalException.Type.VALIDATION, "Verification expiration is required.");
        this.emailVerificationCode = code;
        this.emailVerificationExpiresAt = new Date(expiresAt.getTime());
    }

    public void verifyEmail(String code, Date verifiedAt) {
        require(code != null && !code.isBlank(),
                RentalException.Type.VALIDATION, "Verification code is required.", "code");
        require(emailVerificationCode != null && emailVerificationExpiresAt != null,
                RentalException.Type.VALIDATION, "Email verification was not requested.", "code");
        require(verifiedAt != null && !verifiedAt.after(emailVerificationExpiresAt),
                RentalException.Type.VALIDATION, "Verification code has expired.", "code");
        require(emailVerificationCode.equals(code),
                RentalException.Type.VALIDATION, "Verification code is invalid.", "code");
        this.emailVerified = true;
        clearEmailVerification();
    }

    private void resetEmailVerification() {
        this.emailVerified = false;
        clearEmailVerification();
    }

    private void clearEmailVerification() {
        this.emailVerificationCode = null;
        this.emailVerificationExpiresAt = null;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getSurname() { return surname; }

    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public String getBio() { return bio; }

    public boolean isEmailVerified() { return emailVerified; }

    public boolean isEmailVerificationPending() {
        return !emailVerified && emailVerificationCode != null;
    }

    public UserRole getRole() { return role; }
}

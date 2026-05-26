package sk.fsa.rental.domain;

import sk.fsa.rental.domain.predicate.listing.IsOwnedByPredicate;

import java.util.Arrays;

public class Photo {
    private Long id;
    private String altText;
    private String contentType;
    private String originalFilename;
    private Integer position;
    private byte[] data;
    private Listing listing;

    public Photo() {
    }

    public Photo(byte[] data, String contentType, String originalFilename, String altText, Integer position) {
        this.data = Arrays.copyOf(data, data.length);
        this.contentType = contentType;
        this.originalFilename = originalFilename;
        this.altText = altText;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public boolean isPublicCover() {
        return listing != null
                && ListingStatus.ACTIVE.equals(listing.getStatus())
                && position != null
                && position == 0;
    }

    public boolean canBeViewedBy(User requester) {
        if (requester == null || listing == null) {
            return false;
        }
        if (IsOwnedByPredicate.INSTANCE.test(listing.getOwner(), requester)) {
            return true;
        }
        return ListingStatus.ACTIVE.equals(listing.getStatus());
    }

    public String getAltText() { return altText; }
    public String getContentType() { return contentType; }
    public String getOriginalFilename() { return originalFilename; }
    public Integer getPosition() { return position; }
    public byte[] getData() { return Arrays.copyOf(data, data.length); }
    public Listing getListing() { return listing; }

    // called by Listing.addPhoto() to wire the relationship
    void setListing(Listing listing) { this.listing = listing; }
}

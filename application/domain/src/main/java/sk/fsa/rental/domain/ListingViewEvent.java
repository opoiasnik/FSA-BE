package sk.fsa.rental.domain;

import java.util.Date;

public class ListingViewEvent {

    private Long id;
    private Long listingId;
    private Long ownerId;
    private Date viewedAt;

    public ListingViewEvent() {}

    public ListingViewEvent(Long listingId, Long ownerId) {
        this.listingId = listingId;
        this.ownerId = ownerId;
        this.viewedAt = new Date();
    }

    public Long getId() { return id; }

    public Long getListingId() { return listingId; }

    public Long getOwnerId() { return ownerId; }

    public Date getViewedAt() { return viewedAt; }
}

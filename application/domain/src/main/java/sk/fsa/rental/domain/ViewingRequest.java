package sk.fsa.rental.domain;

import java.util.Date;

public class ViewingRequest {
    private Long id;
    private Date requestedDate;
    private ViewingStatus status;
    private String note;
    private User requester;
    private User owner;
    private Listing listing;

    public ViewingRequest() {
        this.status = ViewingStatus.PENDING;
    }

    public void approve() {
        require(status == ViewingStatus.PENDING, "Only PENDING requests can be approved.");
        this.status = ViewingStatus.APPROVED;
    }

    public void reject() {
        require(status == ViewingStatus.PENDING, "Only PENDING requests can be rejected.");
        this.status = ViewingStatus.REJECTED;
    }

    public void cancel() {
        require(status == ViewingStatus.PENDING || status == ViewingStatus.APPROVED,
                "Only PENDING or APPROVED requests can be cancelled.");
        this.status = ViewingStatus.CANCELLED;
    }

    private void require(boolean condition, String message) {
        if (!condition) {
            throw new RentalException(RentalException.Type.VALIDATION, message);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getRequestedDate() { return requestedDate; }
    public void setRequestedDate(Date requestedDate) { this.requestedDate = requestedDate; }

    public ViewingStatus getStatus() { return status; }
    public void setStatus(ViewingStatus status) { this.status = status; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public User getRequester() { return requester; }
    public void setRequester(User requester) { this.requester = requester; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public Listing getListing() { return listing; }
    public void setListing(Listing listing) { this.listing = listing; }
}

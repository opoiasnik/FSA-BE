package sk.fsa.rental.domain;

import sk.fsa.rental.domain.predicate.viewingrequest.IsViewingCancellablePredicate;
import sk.fsa.rental.domain.predicate.viewingrequest.IsViewingPendingPredicate;

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
        if (!IsViewingPendingPredicate.INSTANCE.test(status))
            throw new RentalException(RentalException.Type.VALIDATION, "Only PENDING requests can be approved.");
        this.status = ViewingStatus.APPROVED;
    }

    public void reject() {
        if (!IsViewingPendingPredicate.INSTANCE.test(status))
            throw new RentalException(RentalException.Type.VALIDATION, "Only PENDING requests can be rejected.");
        this.status = ViewingStatus.REJECTED;
    }

    public void cancel() {
        if (!IsViewingCancellablePredicate.INSTANCE.test(status))
            throw new RentalException(RentalException.Type.VALIDATION, "Only PENDING or APPROVED requests can be cancelled.");
        this.status = ViewingStatus.CANCELLED;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getRequestedDate() { return requestedDate; }
    public void setRequestedDate(Date requestedDate) { this.requestedDate = requestedDate; }

    public ViewingStatus getStatus() { return status; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public User getRequester() { return requester; }
    public void setRequester(User requester) { this.requester = requester; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public Listing getListing() { return listing; }
    public void setListing(Listing listing) { this.listing = listing; }
}

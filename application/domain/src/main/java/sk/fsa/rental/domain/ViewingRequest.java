package sk.fsa.rental.domain;

import sk.fsa.rental.domain.predicate.listing.IsListingActivePredicate;
import sk.fsa.rental.domain.predicate.viewingrequest.HasRequiredFieldsPredicate;
import sk.fsa.rental.domain.predicate.viewingrequest.IsNotOwnListingPredicate;
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

    public void validateForCreation() {
        require(HasRequiredFieldsPredicate.INSTANCE.test(this),
                RentalException.Type.VALIDATION,
                "ViewingRequest must have requester, owner, listing and requested date.");
        require(IsListingActivePredicate.INSTANCE.test(listing),
                RentalException.Type.VALIDATION,
                "Cannot request a viewing for an inactive listing.");
        require(IsNotOwnListingPredicate.INSTANCE.test(this),
                RentalException.Type.VALIDATION,
                "Cannot request a viewing on your own listing.");
    }

    public void approve(User editor) {
        require(isOwner(editor),
                RentalException.Type.FORBIDDEN, "Only the owner can approve this viewing.");
        require(IsViewingPendingPredicate.INSTANCE.test(status),
                RentalException.Type.VALIDATION, "Only PENDING requests can be approved.");
        this.status = ViewingStatus.APPROVED;
    }

    public void reject(User editor) {
        require(isOwner(editor),
                RentalException.Type.FORBIDDEN, "Only the owner can reject this viewing.");
        require(IsViewingPendingPredicate.INSTANCE.test(status),
                RentalException.Type.VALIDATION, "Only PENDING requests can be rejected.");
        this.status = ViewingStatus.REJECTED;
    }

    public void cancel(User editor) {
        require(isRequester(editor),
                RentalException.Type.FORBIDDEN, "Only the requester can cancel this viewing.");
        require(IsViewingCancellablePredicate.INSTANCE.test(status),
                RentalException.Type.VALIDATION, "Only PENDING or APPROVED requests can be cancelled.");
        this.status = ViewingStatus.CANCELLED;
    }

    private boolean isOwner(User user) {
        return user != null && owner != null && user.getId() != null && user.getId().equals(owner.getId());
    }

    private boolean isRequester(User user) {
        return user != null && requester != null && user.getId() != null && user.getId().equals(requester.getId());
    }

    private void require(boolean valid, RentalException.Type type, String message) {
        if (!valid) {
            throw new RentalException(type, message);
        }
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

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

    public ViewingRequest(Date requestedDate, String note) {
        this();
        this.requestedDate = requestedDate;
        this.note = note;
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

    public void assignParticipants(Listing listing, User requester) {
        require(listing != null,
                RentalException.Type.VALIDATION, "Listing is required.");
        require(requester != null,
                RentalException.Type.VALIDATION, "Requester is required.");
        this.listing = listing;
        this.requester = requester;
        this.owner = listing.getOwner();
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
    void setId(Long id) { this.id = id; }

    public Date getRequestedDate() { return requestedDate; }
    void setRequestedDate(Date requestedDate) { this.requestedDate = requestedDate; }

    public ViewingStatus getStatus() { return status; }

    public String getNote() { return note; }
    void setNote(String note) { this.note = note; }

    public User getRequester() { return requester; }

    public User getOwner() { return owner; }

    public Listing getListing() { return listing; }
}

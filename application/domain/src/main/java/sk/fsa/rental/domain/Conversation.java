package sk.fsa.rental.domain;

import sk.fsa.rental.domain.predicate.conversation.HasRequiredParticipantsPredicate;
import sk.fsa.rental.domain.predicate.conversation.IsConversationParticipantPredicate;
import sk.fsa.rental.domain.predicate.conversation.IsNotOwnListingConversationPredicate;
import sk.fsa.rental.domain.predicate.listing.IsListingActivePredicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Conversation {
    private Long id;
    private Listing listing;
    private User owner;
    private User requester;
    private Date createdAt;
    private Date updatedAt;
    private List<Message> messages;

    public Conversation() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.messages = new ArrayList<>();
    }

    public void assignParticipants(Listing listing, User requester) {
        require(listing != null,
                RentalException.Type.VALIDATION, "Listing is required.");
        require(requester != null,
                RentalException.Type.UNAUTHORIZED, "Requester is required.");
        this.listing = listing;
        this.owner = listing.getOwner();
        this.requester = requester;
    }

    public void validateForCreation() {
        require(HasRequiredParticipantsPredicate.INSTANCE.test(this),
                RentalException.Type.VALIDATION, "Conversation must have listing, owner and requester.");
        require(IsListingActivePredicate.INSTANCE.test(listing),
                RentalException.Type.VALIDATION, "Cannot start a conversation for an inactive listing.");
        require(IsNotOwnListingConversationPredicate.INSTANCE.test(this),
                RentalException.Type.VALIDATION, "Cannot start a conversation on your own listing.");
        if (createdAt == null) {
            createdAt = new Date();
        }
        if (updatedAt == null) {
            updatedAt = createdAt;
        }
    }

    public Message addMessage(User sender, String text) {
        require(IsConversationParticipantPredicate.INSTANCE.test(this, sender),
                RentalException.Type.FORBIDDEN, "Only conversation participants can send messages.");
        Message message = new Message(this, sender, text);
        messages.add(message);
        updatedAt = message.getSentAt();
        return message;
    }

    public void requireParticipant(User user) {
        require(IsConversationParticipantPredicate.INSTANCE.test(this, user),
                RentalException.Type.FORBIDDEN, "Only conversation participants can access this conversation.");
    }

    public void markRead(User reader) {
        requireParticipant(reader);
        messages.forEach(message -> message.markReadBy(reader));
    }

    public int unreadCountFor(User user) {
        requireParticipant(user);
        return (int) messages.stream()
                .filter(message -> message.getReadAt() == null)
                .filter(message -> !message.getSender().getId().equals(user.getId()))
                .count();
    }

    public User peerFor(User user) {
        requireParticipant(user);
        if (owner.getId().equals(user.getId())) {
            return requester;
        }
        return owner;
    }

    private void require(boolean valid, RentalException.Type type, String message) {
        if (!valid) {
            throw new RentalException(type, message);
        }
    }

    public Long getId() { return id; }

    public Listing getListing() { return listing; }

    public User getOwner() { return owner; }

    public User getRequester() { return requester; }

    public Date getCreatedAt() { return createdAt; }

    public Date getUpdatedAt() { return updatedAt; }

    public List<Message> getMessages() { return Collections.unmodifiableList(messages); }
}

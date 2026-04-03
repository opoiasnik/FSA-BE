package sk.fsa.rental.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Conversation {
    private Long id;
    private Date createdAt;
    private Listing listing;
    private List<User> participants;
    private List<Message> messages;

    public Conversation() {
        this.createdAt = new Date();
        this.participants = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public void addMessage(Message message) {
        if (message == null) {
            throw new RentalException(RentalException.Type.VALIDATION, "Message cannot be null.");
        }
        message.setConversation(this);
        messages.add(message);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Listing getListing() { return listing; }
    public void setListing(Listing listing) { this.listing = listing; }

    public List<User> getParticipants() { return Collections.unmodifiableList(participants); }
    public void setParticipants(List<User> participants) { this.participants = participants; }

    public List<Message> getMessages() { return Collections.unmodifiableList(messages); }
    public void setMessages(List<Message> messages) { this.messages = messages; }
}

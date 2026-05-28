package sk.fsa.rental.domain;

import sk.fsa.rental.domain.predicate.message.HasRequiredMessageTextPredicate;

import java.util.Date;

public class Message {
    private Long id;
    private Conversation conversation;
    private User sender;
    private String text;
    private Date sentAt;
    private Date readAt;

    public Message() {
    }

    Message(Conversation conversation, User sender, String text) {
        this.conversation = conversation;
        this.sender = sender;
        this.text = text == null ? null : text.trim();
        this.sentAt = new Date();
        validateForCreation();
    }

    public void validateForCreation() {
        require(conversation != null,
                RentalException.Type.VALIDATION, "Conversation is required.");
        require(sender != null && sender.getId() != null,
                RentalException.Type.UNAUTHORIZED, "Sender is required.");
        require(HasRequiredMessageTextPredicate.INSTANCE.test(text),
                RentalException.Type.VALIDATION, "Message text is required and must be 2000 characters or shorter.");
        if (sentAt == null) {
            sentAt = new Date();
        }
    }

    public void markReadBy(User reader) {
        require(reader != null && reader.getId() != null,
                RentalException.Type.UNAUTHORIZED, "Reader is required.");
        if (!sender.getId().equals(reader.getId()) && readAt == null) {
            readAt = new Date();
        }
    }

    private void require(boolean valid, RentalException.Type type, String message) {
        if (!valid) {
            throw new RentalException(type, message);
        }
    }

    public Long getId() { return id; }

    public Conversation getConversation() { return conversation; }

    public User getSender() { return sender; }

    public String getText() { return text; }

    public Date getSentAt() { return sentAt; }

    public Date getReadAt() { return readAt; }
}

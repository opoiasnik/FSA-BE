package sk.fsa.rental.domain;

import java.util.Date;

public class Message {
    private Long id;
    private String text;
    private Date createdAt;
    private Boolean isRead;
    private User sender;
    private Conversation conversation;

    public Message() {
        this.createdAt = new Date();
        this.isRead = false;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Date getCreatedAt() { return createdAt; }

    public Boolean getIsRead() { return isRead; }

    public User getSender() { return sender; }
    public void setSender(User sender) { this.sender = sender; }

    public Conversation getConversation() { return conversation; }
    public void setConversation(Conversation conversation) { this.conversation = conversation; }
}

package sk.fsa.rental.domain.repository;

import sk.fsa.rental.domain.Message;

import java.util.Collection;

public interface MessageRepository {
    void createMessage(Message message);
    Collection<Message> readAll();
    void updateMessage(Message message);
    void deleteMessage(Long id);
    Collection<Message> findByConversationId(Long conversationId);
}

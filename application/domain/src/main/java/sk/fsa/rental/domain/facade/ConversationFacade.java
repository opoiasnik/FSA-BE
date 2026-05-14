package sk.fsa.rental.domain.facade;

import sk.fsa.rental.domain.Conversation;
import sk.fsa.rental.domain.Message;
import sk.fsa.rental.domain.User;

import java.util.List;

public interface ConversationFacade {
    Conversation open(Long listingId, User requester, String initialMessage);
    List<Conversation> listForUser(User user);
    Conversation getConversation(Long conversationId, User user);
    Message sendMessage(Long conversationId, User sender, String text);
    Conversation markRead(Long conversationId, User reader);
}

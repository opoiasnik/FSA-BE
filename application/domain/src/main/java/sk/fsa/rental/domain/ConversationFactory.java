package sk.fsa.rental.domain;

public class ConversationFactory {

    public Conversation create(Listing listing, User requester, String initialMessage) {
        Conversation conversation = new Conversation();
        conversation.assignParticipants(listing, requester);
        conversation.validateForCreation();
        if (initialMessage != null && !initialMessage.isBlank()) {
            conversation.addMessage(requester, initialMessage);
        }
        return conversation;
    }
}

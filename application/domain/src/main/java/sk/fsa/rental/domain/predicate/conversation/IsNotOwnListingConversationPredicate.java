package sk.fsa.rental.domain.predicate.conversation;

import sk.fsa.rental.domain.Conversation;

import java.util.function.Predicate;

public final class IsNotOwnListingConversationPredicate implements Predicate<Conversation> {
    public static final IsNotOwnListingConversationPredicate INSTANCE = new IsNotOwnListingConversationPredicate();

    private IsNotOwnListingConversationPredicate() {
    }

    @Override
    public boolean test(Conversation conversation) {
        return conversation != null
                && conversation.getOwner() != null
                && conversation.getRequester() != null
                && conversation.getOwner().getId() != null
                && conversation.getRequester().getId() != null
                && !conversation.getOwner().getId().equals(conversation.getRequester().getId());
    }
}

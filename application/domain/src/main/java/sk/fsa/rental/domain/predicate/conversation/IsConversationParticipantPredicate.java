package sk.fsa.rental.domain.predicate.conversation;

import sk.fsa.rental.domain.Conversation;
import sk.fsa.rental.domain.User;

import java.util.function.BiPredicate;

public final class IsConversationParticipantPredicate implements BiPredicate<Conversation, User> {
    public static final IsConversationParticipantPredicate INSTANCE = new IsConversationParticipantPredicate();

    private IsConversationParticipantPredicate() {
    }

    @Override
    public boolean test(Conversation conversation, User user) {
        return conversation != null
                && user != null
                && user.getId() != null
                && (sameUser(conversation.getOwner(), user) || sameUser(conversation.getRequester(), user));
    }

    private boolean sameUser(User first, User second) {
        return first != null
                && second != null
                && first.getId() != null
                && first.getId().equals(second.getId());
    }
}

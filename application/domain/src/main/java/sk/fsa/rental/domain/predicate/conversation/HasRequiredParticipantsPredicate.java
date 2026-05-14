package sk.fsa.rental.domain.predicate.conversation;

import sk.fsa.rental.domain.Conversation;

import java.util.function.Predicate;

public final class HasRequiredParticipantsPredicate implements Predicate<Conversation> {
    public static final HasRequiredParticipantsPredicate INSTANCE = new HasRequiredParticipantsPredicate();

    private HasRequiredParticipantsPredicate() {
    }

    @Override
    public boolean test(Conversation conversation) {
        return conversation != null
                && conversation.getListing() != null
                && conversation.getOwner() != null
                && conversation.getRequester() != null
                && conversation.getOwner().getId() != null
                && conversation.getRequester().getId() != null;
    }
}

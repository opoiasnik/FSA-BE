package sk.fsa.rental.domain.repository;

import sk.fsa.rental.domain.Conversation;

import java.util.Collection;
import java.util.Optional;

public interface ConversationRepository {
    void createConversation(Conversation conversation);
    Optional<Conversation> findById(Long id);
    Collection<Conversation> findByUserId(Long userId);
    Collection<Conversation> findByListingId(Long listingId);
}

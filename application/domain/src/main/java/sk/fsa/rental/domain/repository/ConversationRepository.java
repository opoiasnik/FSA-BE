package sk.fsa.rental.domain.repository;

import sk.fsa.rental.domain.Conversation;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository {
    Conversation save(Conversation conversation);
    Optional<Conversation> findById(Long id);
    Optional<Conversation> findByListingIdAndOwnerIdAndRequesterId(Long listingId, Long ownerId, Long requesterId);
    List<Conversation> findByListingId(Long listingId);
    List<Conversation> findByParticipantId(Long participantId);
}

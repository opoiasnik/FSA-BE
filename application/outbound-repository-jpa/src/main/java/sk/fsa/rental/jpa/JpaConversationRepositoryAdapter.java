package sk.fsa.rental.jpa;

import org.springframework.stereotype.Repository;
import sk.fsa.rental.domain.Conversation;
import sk.fsa.rental.domain.repository.ConversationRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaConversationRepositoryAdapter implements ConversationRepository {

    private final ConversationSpringDataRepository conversationSpringDataRepository;

    public JpaConversationRepositoryAdapter(ConversationSpringDataRepository conversationSpringDataRepository) {
        this.conversationSpringDataRepository = conversationSpringDataRepository;
    }

    @Override
    public Conversation save(Conversation conversation) {
        return conversationSpringDataRepository.save(conversation);
    }

    @Override
    public Optional<Conversation> findById(Long id) {
        return conversationSpringDataRepository.findById(id);
    }

    @Override
    public Optional<Conversation> findByListingIdAndOwnerIdAndRequesterId(Long listingId, Long ownerId, Long requesterId) {
        return conversationSpringDataRepository.findByListingIdAndOwnerIdAndRequesterId(listingId, ownerId, requesterId);
    }

    @Override
    public List<Conversation> findByParticipantId(Long participantId) {
        return conversationSpringDataRepository.findByOwnerIdOrRequesterIdOrderByUpdatedAtDesc(participantId, participantId);
    }
}

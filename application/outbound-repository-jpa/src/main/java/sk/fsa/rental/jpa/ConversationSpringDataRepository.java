package sk.fsa.rental.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.fsa.rental.domain.Conversation;

import java.util.List;
import java.util.Optional;

interface ConversationSpringDataRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByListingIdAndOwnerIdAndRequesterId(Long listingId, Long ownerId, Long requesterId);
    List<Conversation> findByOwnerIdOrRequesterIdOrderByUpdatedAtDesc(Long ownerId, Long requesterId);
}

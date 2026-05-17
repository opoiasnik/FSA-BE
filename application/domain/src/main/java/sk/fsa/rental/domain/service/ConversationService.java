package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.Conversation;
import sk.fsa.rental.domain.ConversationFactory;
import sk.fsa.rental.domain.Listing;
import sk.fsa.rental.domain.Message;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.ConversationFacade;
import sk.fsa.rental.domain.facade.NotificationEmailFacade;
import sk.fsa.rental.domain.repository.ConversationRepository;
import sk.fsa.rental.domain.repository.ListingRepository;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ConversationService implements ConversationFacade {

    private final ConversationRepository conversationRepository;
    private final ListingRepository listingRepository;
    private final ConversationFactory conversationFactory;
    private final NotificationEmailFacade notificationEmailFacade;

    public ConversationService(ConversationRepository conversationRepository,
                               ListingRepository listingRepository,
                               ConversationFactory conversationFactory,
                               NotificationEmailFacade notificationEmailFacade) {
        this.conversationRepository = conversationRepository;
        this.listingRepository = listingRepository;
        this.conversationFactory = conversationFactory;
        this.notificationEmailFacade = notificationEmailFacade;
    }

    @Override
    public Conversation open(Long listingId, User requester, String initialMessage) {
        requireIdentifiedUser(requester, "Requester is required.");
        Listing listing = findListingOrThrow(listingId);

        return conversationRepository
                .findByListingIdAndOwnerIdAndRequesterId(listingId, listing.getOwner().getId(), requester.getId())
                .map(conversation -> appendInitialMessage(conversation, requester, initialMessage))
                .orElseGet(() -> create(listing, requester, initialMessage));
    }

    @Override
    public List<Conversation> listForUser(User user) {
        requireIdentifiedUser(user, "User is required.");
        return conversationRepository.findByParticipantId(user.getId()).stream()
                .peek(conversation -> conversation.requireParticipant(user))
                .sorted(Comparator.comparing(Conversation::getUpdatedAt,
                        Comparator.nullsFirst(Date::compareTo)).reversed())
                .toList();
    }

    @Override
    public Conversation getConversation(Long conversationId, User user) {
        Conversation conversation = findOrThrow(conversationId);
        conversation.requireParticipant(user);
        return conversation;
    }

    @Override
    public Message sendMessage(Long conversationId, User sender, String text) {
        Conversation conversation = getConversation(conversationId, sender);
        Message message = conversation.addMessage(sender, text);
        conversationRepository.save(conversation);
        notificationEmailFacade.messageSent(conversation, message, sender);
        return message;
    }

    @Override
    public Conversation markRead(Long conversationId, User reader) {
        Conversation conversation = getConversation(conversationId, reader);
        conversation.markRead(reader);
        return conversationRepository.save(conversation);
    }

    private Conversation findOrThrow(Long conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Conversation not found."));
    }

    private Listing findListingOrThrow(Long listingId) {
        return listingRepository.findById(listingId)
                .orElseThrow(() -> new RentalException(RentalException.Type.NOT_FOUND, "Listing not found."));
    }

    private Conversation create(Listing listing, User requester, String initialMessage) {
        Conversation conversation = conversationFactory.create(listing, requester, initialMessage);
        return conversationRepository.save(conversation);
    }

    private Conversation appendInitialMessage(Conversation conversation, User requester, String initialMessage) {
        conversation.requireParticipant(requester);
        addInitialMessageIfPresent(conversation, requester, initialMessage);
        return conversationRepository.save(conversation);
    }

    private void addInitialMessageIfPresent(Conversation conversation, User requester, String initialMessage) {
        if (initialMessage != null && !initialMessage.isBlank()) {
            conversation.addMessage(requester, initialMessage);
        }
    }

    private void requireIdentifiedUser(User user, String message) {
        require(user != null && user.getId() != null, RentalException.Type.UNAUTHORIZED, message);
    }

    private void require(boolean valid, RentalException.Type type, String message) {
        if (!valid) {
            throw new RentalException(type, message);
        }
    }
}

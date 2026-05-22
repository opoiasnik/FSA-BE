package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.Conversation;
import sk.fsa.rental.domain.Message;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.ViewingRequest;
import sk.fsa.rental.domain.email.EmailMessage;
import sk.fsa.rental.domain.email.NotificationEmailTemplate;
import sk.fsa.rental.domain.facade.NotificationEmailFacade;
import sk.fsa.rental.domain.repository.EmailSenderRepository;

public class NotificationEmailService implements NotificationEmailFacade {

    private final EmailSenderRepository emailSenderRepository;
    private final NotificationEmailTemplate notificationEmailTemplate;

    public NotificationEmailService(EmailSenderRepository emailSenderRepository,
                                    NotificationEmailTemplate notificationEmailTemplate) {
        this.emailSenderRepository = emailSenderRepository;
        this.notificationEmailTemplate = notificationEmailTemplate;
    }

    @Override
    public void messageSent(Conversation conversation, Message message, User sender) {
        User recipient = conversation.peerFor(sender);
        if (recipient == null || !recipient.canReceiveMessageEmailNotifications()) {
            return;
        }

        EmailMessage email = notificationEmailTemplate.messageReceived(conversation, message, sender, recipient);
        emailSenderRepository.send(recipient.getEmail(), email.getSubject(), email.getBody());
    }

    @Override
    public void viewingRequestCreated(ViewingRequest viewingRequest) {
        User owner = viewingRequest.getOwner();
        if (owner == null || !owner.canReceiveViewingRequestEmailNotifications()) {
            return;
        }

        EmailMessage email = notificationEmailTemplate.viewingRequestCreated(viewingRequest);
        emailSenderRepository.send(owner.getEmail(), email.getSubject(), email.getBody());
    }

    @Override
    public void viewingStatusChanged(ViewingRequest viewingRequest) {
        User requester = viewingRequest.getRequester();
        if (requester == null || !requester.canReceiveViewingEmailNotifications()) {
            return;
        }

        EmailMessage email = notificationEmailTemplate.viewingStatusChanged(viewingRequest);
        emailSenderRepository.send(requester.getEmail(), email.getSubject(), email.getBody());
    }

    @Override
    public void viewingCancelled(ViewingRequest viewingRequest) {
        User owner = viewingRequest.getOwner();
        if (owner == null || !owner.canReceiveViewingRequestEmailNotifications()) {
            return;
        }

        EmailMessage email = notificationEmailTemplate.viewingCancelled(viewingRequest);
        emailSenderRepository.send(owner.getEmail(), email.getSubject(), email.getBody());
    }
}

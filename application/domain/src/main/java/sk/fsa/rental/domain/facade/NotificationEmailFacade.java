package sk.fsa.rental.domain.facade;

import sk.fsa.rental.domain.Conversation;
import sk.fsa.rental.domain.Message;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.ViewingRequest;

public interface NotificationEmailFacade {

    void messageSent(Conversation conversation, Message message, User sender);

    void viewingRequestCreated(ViewingRequest viewingRequest);

    void viewingStatusChanged(ViewingRequest viewingRequest);

    void viewingCancelled(ViewingRequest viewingRequest);
}

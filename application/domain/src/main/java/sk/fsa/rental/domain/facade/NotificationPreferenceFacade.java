package sk.fsa.rental.domain.facade;

import sk.fsa.rental.domain.User;

public interface NotificationPreferenceFacade {

    User update(User user, boolean messageEmailNotifications,
                boolean viewingEmailNotifications, boolean viewingRequestEmailNotifications);
}

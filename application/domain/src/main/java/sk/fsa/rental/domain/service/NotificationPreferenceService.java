package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.NotificationPreferenceFacade;
import sk.fsa.rental.domain.repository.UserRepository;

public class NotificationPreferenceService implements NotificationPreferenceFacade {

    private final UserRepository userRepository;

    public NotificationPreferenceService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User update(User user, boolean messageEmailNotifications,
                       boolean viewingEmailNotifications, boolean viewingRequestEmailNotifications) {
        user.updateNotificationPreferences(messageEmailNotifications, viewingEmailNotifications, viewingRequestEmailNotifications);
        return userRepository.save(user);
    }
}

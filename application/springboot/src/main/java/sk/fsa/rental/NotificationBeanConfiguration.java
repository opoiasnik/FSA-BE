package sk.fsa.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.fsa.rental.domain.email.NotificationEmailTemplate;
import sk.fsa.rental.domain.facade.NotificationEmailFacade;
import sk.fsa.rental.domain.facade.NotificationPreferenceFacade;
import sk.fsa.rental.domain.repository.EmailSenderRepository;
import sk.fsa.rental.domain.repository.UserRepository;
import sk.fsa.rental.domain.service.NotificationEmailService;
import sk.fsa.rental.domain.service.NotificationPreferenceService;

@Configuration
public class NotificationBeanConfiguration {

    @Bean
    public NotificationPreferenceFacade notificationPreferenceFacade(UserRepository userRepository) {
        return new NotificationPreferenceService(userRepository);
    }

    @Bean
    public NotificationEmailFacade notificationEmailFacade(EmailSenderRepository emailSenderRepository,
                                                           NotificationEmailTemplate notificationEmailTemplate) {
        return new NotificationEmailService(emailSenderRepository, notificationEmailTemplate);
    }

    @Bean
    public NotificationEmailTemplate notificationEmailTemplate() {
        return new NotificationEmailTemplate();
    }
}

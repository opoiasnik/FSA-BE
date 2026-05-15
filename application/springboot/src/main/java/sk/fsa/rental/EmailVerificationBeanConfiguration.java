package sk.fsa.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.fsa.rental.domain.email.EmailVerificationTemplate;
import sk.fsa.rental.domain.facade.EmailVerificationFacade;
import sk.fsa.rental.domain.repository.EmailSenderRepository;
import sk.fsa.rental.domain.repository.UserRepository;
import sk.fsa.rental.domain.service.EmailVerificationService;

@Configuration
public class EmailVerificationBeanConfiguration {

    @Bean
    public EmailVerificationFacade emailVerificationFacade(UserRepository userRepository,
                                                           EmailSenderRepository emailSenderRepository,
                                                           EmailVerificationTemplate emailVerificationTemplate) {
        return new EmailVerificationService(userRepository, emailSenderRepository, emailVerificationTemplate);
    }

    @Bean
    public EmailVerificationTemplate emailVerificationTemplate() {
        return new EmailVerificationTemplate();
    }
}

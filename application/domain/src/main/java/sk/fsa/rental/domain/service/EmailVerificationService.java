package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.email.EmailMessage;
import sk.fsa.rental.domain.email.EmailVerificationTemplate;
import sk.fsa.rental.domain.facade.EmailVerificationFacade;
import sk.fsa.rental.domain.repository.EmailSenderRepository;
import sk.fsa.rental.domain.repository.UserRepository;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class EmailVerificationService implements EmailVerificationFacade {

    private static final Duration EMAIL_VERIFICATION_TTL = Duration.ofMinutes(15);
    private static final SecureRandom RANDOM = new SecureRandom();

    private final UserRepository userRepository;
    private final EmailSenderRepository emailSenderRepository;
    private final EmailVerificationTemplate emailVerificationTemplate;

    public EmailVerificationService(UserRepository userRepository, EmailSenderRepository emailSenderRepository,
                                    EmailVerificationTemplate emailVerificationTemplate) {
        this.userRepository = userRepository;
        this.emailSenderRepository = emailSenderRepository;
        this.emailVerificationTemplate = emailVerificationTemplate;
    }

    @Override
    public void request(User user) {
        String code = generateVerificationCode();
        Date expiresAt = Date.from(Instant.now().plus(EMAIL_VERIFICATION_TTL));
        user.startEmailVerification(code, expiresAt);
        userRepository.save(user);
        EmailMessage message = emailVerificationTemplate.create(user, code);
        emailSenderRepository.send(user.getEmail(), message.getSubject(), message.getBody());
    }

    @Override
    public User confirm(User user, String code) {
        user.verifyEmail(code, new Date());
        return userRepository.save(user);
    }

    private String generateVerificationCode() {
        return String.format("%06d", RANDOM.nextInt(1_000_000));
    }
}

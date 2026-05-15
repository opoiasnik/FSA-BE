package sk.fsa.rental.jpa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import sk.fsa.rental.domain.repository.EmailSenderRepository;

@Component
public class SmtpEmailSenderAdapter implements EmailSenderRepository {

    private final JavaMailSender mailSender;

    @Value("${rental.mail.from}")
    private String from;

    public SmtpEmailSenderAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException ex) {
            throw new IllegalStateException("Unable to prepare email message.", ex);
        }
    }
}

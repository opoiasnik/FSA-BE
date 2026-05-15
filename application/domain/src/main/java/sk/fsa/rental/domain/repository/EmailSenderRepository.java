package sk.fsa.rental.domain.repository;

public interface EmailSenderRepository {
    void send(String to, String subject, String body);
}

package sk.fsa.rental.domain;

public class RentalException extends RuntimeException {

    public enum Type {
        VALIDATION,
        NOT_FOUND,
        UNAUTHORIZED,
        FORBIDDEN
    }

    private final Type type;

    public RentalException(Type type, String message) {
        super(message);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}

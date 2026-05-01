package sk.fsa.rental.domain;

public class RentalException extends RuntimeException {

    public enum Type {
        VALIDATION,
        NOT_FOUND,
        UNAUTHORIZED,
        FORBIDDEN
    }

    private final Type type;
    private final String field;

    public RentalException(Type type, String message) {
        this(type, message, null);
    }

    public RentalException(Type type, String message, String field) {
        super(message);
        this.type = type;
        this.field = field;
    }

    public Type getType() {
        return type;
    }

    public String getField() {
        return field;
    }
}

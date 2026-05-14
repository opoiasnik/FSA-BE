package sk.fsa.rental.domain.predicate.message;

import java.util.function.Predicate;

public final class HasRequiredMessageTextPredicate implements Predicate<String> {
    public static final HasRequiredMessageTextPredicate INSTANCE = new HasRequiredMessageTextPredicate();

    private static final int MAX_LENGTH = 2000;

    private HasRequiredMessageTextPredicate() {
    }

    @Override
    public boolean test(String text) {
        return text != null && !text.isBlank() && text.length() <= MAX_LENGTH;
    }
}

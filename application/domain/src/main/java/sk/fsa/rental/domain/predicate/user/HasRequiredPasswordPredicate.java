package sk.fsa.rental.domain.predicate.user;

import java.util.function.Predicate;

public final class HasRequiredPasswordPredicate implements Predicate<String> {

    public static final HasRequiredPasswordPredicate INSTANCE = new HasRequiredPasswordPredicate();

    private HasRequiredPasswordPredicate() {}

    @Override
    public boolean test(String passwordHash) {
        return passwordHash != null && !passwordHash.isEmpty();
    }
}

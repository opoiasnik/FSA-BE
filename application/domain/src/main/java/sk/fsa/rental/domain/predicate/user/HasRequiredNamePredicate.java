package sk.fsa.rental.domain.predicate.user;

import java.util.function.Predicate;

public final class HasRequiredNamePredicate implements Predicate<String> {

    public static final HasRequiredNamePredicate INSTANCE = new HasRequiredNamePredicate();

    private HasRequiredNamePredicate() {}

    @Override
    public boolean test(String name) {
        return name != null && !name.trim().isEmpty();
    }
}

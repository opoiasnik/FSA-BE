package sk.fsa.rental.domain.predicate.listing;

import java.util.function.Predicate;

public final class HasRequiredDescriptionPredicate implements Predicate<String> {

    public static final HasRequiredDescriptionPredicate INSTANCE = new HasRequiredDescriptionPredicate();

    private HasRequiredDescriptionPredicate() {}

    @Override
    public boolean test(String description) {
        return description != null && !description.trim().isEmpty();
    }
}

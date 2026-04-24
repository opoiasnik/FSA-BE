package sk.fsa.rental.domain.predicate.listing;

import java.util.function.Predicate;

public final class HasRequiredTitlePredicate implements Predicate<String> {

    public static final HasRequiredTitlePredicate INSTANCE = new HasRequiredTitlePredicate();

    private HasRequiredTitlePredicate() {}

    @Override
    public boolean test(String title) {
        return title != null && !title.trim().isEmpty();
    }
}

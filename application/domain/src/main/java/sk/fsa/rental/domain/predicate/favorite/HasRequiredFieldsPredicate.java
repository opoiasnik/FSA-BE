package sk.fsa.rental.domain.predicate.favorite;

import sk.fsa.rental.domain.Favorite;

import java.util.function.Predicate;

public final class HasRequiredFieldsPredicate implements Predicate<Favorite> {

    public static final HasRequiredFieldsPredicate INSTANCE = new HasRequiredFieldsPredicate();

    private HasRequiredFieldsPredicate() {}

    @Override
    public boolean test(Favorite favorite) {
        return favorite != null
                && favorite.getUser() != null
                && favorite.getListing() != null;
    }
}

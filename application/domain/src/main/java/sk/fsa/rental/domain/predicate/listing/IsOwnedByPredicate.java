package sk.fsa.rental.domain.predicate.listing;

import sk.fsa.rental.domain.User;

import java.util.function.BiPredicate;

public final class IsOwnedByPredicate implements BiPredicate<User, User> {

    public static final IsOwnedByPredicate INSTANCE = new IsOwnedByPredicate();

    private IsOwnedByPredicate() {}

    @Override
    public boolean test(User owner, User requester) {
        return owner != null
                && owner.getId() != null
                && requester != null
                && owner.getId().equals(requester.getId());
    }
}

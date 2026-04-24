package sk.fsa.rental.domain.predicate.user;

import sk.fsa.rental.domain.User;

import java.util.function.Predicate;

public final class HasRequiredRolePredicate implements Predicate<User> {

    public static final HasRequiredRolePredicate INSTANCE = new HasRequiredRolePredicate();

    private HasRequiredRolePredicate() {}

    @Override
    public boolean test(User user) {
        return user.getRole() != null;
    }
}

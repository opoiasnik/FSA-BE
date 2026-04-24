package sk.fsa.rental.domain.predicate.user;

import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.UserRole;

import java.util.function.Predicate;

public final class IsOwnerRolePredicate implements Predicate<User> {

    public static final IsOwnerRolePredicate INSTANCE = new IsOwnerRolePredicate();

    private IsOwnerRolePredicate() {}

    @Override
    public boolean test(User user) {
        return user != null && UserRole.OWNER.equals(user.getRole());
    }
}

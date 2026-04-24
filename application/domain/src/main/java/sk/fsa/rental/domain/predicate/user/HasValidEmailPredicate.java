package sk.fsa.rental.domain.predicate.user;

import java.util.function.Predicate;

public final class HasValidEmailPredicate implements Predicate<String> {

    public static final HasValidEmailPredicate INSTANCE = new HasValidEmailPredicate();

    private HasValidEmailPredicate() {}

    @Override
    public boolean test(String email) {
        return email != null && email.contains("@");
    }
}

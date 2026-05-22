package sk.fsa.rental.domain.predicate.user;

import java.util.function.Predicate;

public final class HasValidBioPredicate implements Predicate<String> {

    public static final HasValidBioPredicate INSTANCE = new HasValidBioPredicate();
    private static final int MAX_BIO_LENGTH = 1000;

    private HasValidBioPredicate() {}

    @Override
    public boolean test(String bio) {
        return bio == null || bio.length() <= MAX_BIO_LENGTH;
    }
}

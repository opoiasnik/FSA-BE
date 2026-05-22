package sk.fsa.rental.domain.predicate.user;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public final class HasValidPersonNamePredicate implements Predicate<String> {

    public static final HasValidPersonNamePredicate INSTANCE = new HasValidPersonNamePredicate();
    private static final Pattern PERSON_NAME_PATTERN = Pattern.compile("^[\\p{L}][\\p{L}\\p{M} .'-]{0,79}$");

    private HasValidPersonNamePredicate() {}

    @Override
    public boolean test(String value) {
        return value != null && PERSON_NAME_PATTERN.matcher(value.trim()).matches();
    }
}

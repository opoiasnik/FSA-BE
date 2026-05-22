package sk.fsa.rental.domain.predicate.user;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public final class HasValidEmailPredicate implements Predicate<String> {

    public static final HasValidEmailPredicate INSTANCE = new HasValidEmailPredicate();
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private HasValidEmailPredicate() {}

    @Override
    public boolean test(String email) {
        return email != null && email.length() <= 254 && EMAIL_PATTERN.matcher(email).matches();
    }
}

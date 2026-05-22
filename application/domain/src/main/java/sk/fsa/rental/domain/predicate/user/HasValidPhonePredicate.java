package sk.fsa.rental.domain.predicate.user;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public final class HasValidPhonePredicate implements Predicate<String> {

    public static final HasValidPhonePredicate INSTANCE = new HasValidPhonePredicate();
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9][0-9\\s().-]{6,24}$");

    private HasValidPhonePredicate() {}

    @Override
    public boolean test(String phone) {
        return phone == null || phone.isBlank() || PHONE_PATTERN.matcher(phone.trim()).matches();
    }
}

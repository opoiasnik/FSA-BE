package sk.fsa.rental.domain.predicate.photo;

import java.util.function.Predicate;

public final class HasImageContentTypePredicate implements Predicate<String> {

    public static final HasImageContentTypePredicate INSTANCE = new HasImageContentTypePredicate();

    private HasImageContentTypePredicate() {}

    @Override
    public boolean test(String contentType) {
        return contentType != null && contentType.startsWith("image/");
    }
}

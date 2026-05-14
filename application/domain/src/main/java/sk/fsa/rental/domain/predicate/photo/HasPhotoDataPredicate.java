package sk.fsa.rental.domain.predicate.photo;

import java.util.function.Predicate;

public final class HasPhotoDataPredicate implements Predicate<byte[]> {

    public static final HasPhotoDataPredicate INSTANCE = new HasPhotoDataPredicate();

    private HasPhotoDataPredicate() {}

    @Override
    public boolean test(byte[] data) {
        return data != null && data.length > 0;
    }
}

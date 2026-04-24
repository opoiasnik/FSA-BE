package sk.fsa.rental.domain.predicate.viewingrequest;

import sk.fsa.rental.domain.ViewingStatus;

import java.util.function.Predicate;

public final class IsViewingCancellablePredicate implements Predicate<ViewingStatus> {

    public static final IsViewingCancellablePredicate INSTANCE = new IsViewingCancellablePredicate();

    private IsViewingCancellablePredicate() {}

    @Override
    public boolean test(ViewingStatus status) {
        return ViewingStatus.PENDING.equals(status) || ViewingStatus.APPROVED.equals(status);
    }
}

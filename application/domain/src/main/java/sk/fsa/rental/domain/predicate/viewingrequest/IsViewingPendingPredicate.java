package sk.fsa.rental.domain.predicate.viewingrequest;

import sk.fsa.rental.domain.ViewingStatus;

import java.util.function.Predicate;

public final class IsViewingPendingPredicate implements Predicate<ViewingStatus> {

    public static final IsViewingPendingPredicate INSTANCE = new IsViewingPendingPredicate();

    private IsViewingPendingPredicate() {}

    @Override
    public boolean test(ViewingStatus status) {
        return ViewingStatus.PENDING.equals(status);
    }
}

package sk.fsa.rental.domain.predicate.viewingrequest;

import sk.fsa.rental.domain.ViewingStatus;

import java.util.function.Predicate;

public final class IsViewingActivePredicate implements Predicate<ViewingStatus> {

    public static final IsViewingActivePredicate INSTANCE = new IsViewingActivePredicate();

    private IsViewingActivePredicate() {}

    @Override
    public boolean test(ViewingStatus status) {
        return ViewingStatus.PENDING.equals(status) || ViewingStatus.APPROVED.equals(status);
    }
}

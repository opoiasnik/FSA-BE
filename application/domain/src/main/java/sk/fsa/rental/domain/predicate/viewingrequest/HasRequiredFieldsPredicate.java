package sk.fsa.rental.domain.predicate.viewingrequest;

import sk.fsa.rental.domain.ViewingRequest;

import java.util.function.Predicate;

public final class HasRequiredFieldsPredicate implements Predicate<ViewingRequest> {

    public static final HasRequiredFieldsPredicate INSTANCE = new HasRequiredFieldsPredicate();

    private HasRequiredFieldsPredicate() {}

    @Override
    public boolean test(ViewingRequest viewingRequest) {
        return viewingRequest != null
                && viewingRequest.getRequester() != null
                && viewingRequest.getOwner() != null
                && viewingRequest.getListing() != null
                && viewingRequest.getRequestedDate() != null;
    }
}

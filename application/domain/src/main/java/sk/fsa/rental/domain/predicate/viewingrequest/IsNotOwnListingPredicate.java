package sk.fsa.rental.domain.predicate.viewingrequest;

import sk.fsa.rental.domain.ViewingRequest;

import java.util.function.Predicate;

public final class IsNotOwnListingPredicate implements Predicate<ViewingRequest> {

    public static final IsNotOwnListingPredicate INSTANCE = new IsNotOwnListingPredicate();

    private IsNotOwnListingPredicate() {}

    @Override
    public boolean test(ViewingRequest viewingRequest) {
        if (viewingRequest == null
                || viewingRequest.getRequester() == null
                || viewingRequest.getOwner() == null) {
            return false;
        }
        Long requesterId = viewingRequest.getRequester().getId();
        Long ownerId = viewingRequest.getOwner().getId();
        return requesterId != null && !requesterId.equals(ownerId);
    }
}

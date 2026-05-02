package sk.fsa.rental.domain.predicate.favorite;

import sk.fsa.rental.domain.Favorite;

import java.util.function.Predicate;

public final class IsNotOwnListingPredicate implements Predicate<Favorite> {

    public static final IsNotOwnListingPredicate INSTANCE = new IsNotOwnListingPredicate();

    private IsNotOwnListingPredicate() {}

    @Override
    public boolean test(Favorite favorite) {
        if (favorite == null || favorite.getUser() == null || favorite.getListing() == null) {
            return false;
        }
        Long userId = favorite.getUser().getId();
        Long ownerId = favorite.getListing().getOwner() != null
                ? favorite.getListing().getOwner().getId()
                : null;
        return userId != null && !userId.equals(ownerId);
    }
}

package sk.fsa.rental.domain.facade;

import sk.fsa.rental.domain.OwnerStats;

public interface OwnerStatsFacade {
    OwnerStats getByOwner(Long ownerId);
}

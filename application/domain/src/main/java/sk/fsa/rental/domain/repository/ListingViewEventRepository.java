package sk.fsa.rental.domain.repository;

import sk.fsa.rental.domain.ListingViewEvent;

import java.util.Date;
import java.util.List;

public interface ListingViewEventRepository {

    ListingViewEvent save(ListingViewEvent event);

    long countByOwnerId(Long ownerId);

    List<ListingViewEvent> findByOwnerIdAndViewedAtAfter(Long ownerId, Date since);
}

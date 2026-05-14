package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.ListingStatus;
import sk.fsa.rental.domain.ListingViewEvent;
import sk.fsa.rental.domain.OwnerStats;
import sk.fsa.rental.domain.facade.OwnerStatsFacade;
import sk.fsa.rental.domain.ViewingStatus;
import sk.fsa.rental.domain.repository.FavoriteRepository;
import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.repository.ListingViewEventRepository;
import sk.fsa.rental.domain.repository.ViewingRequestRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OwnerStatsService implements OwnerStatsFacade {

    private static final int TREND_DAYS = 30;

    private final ListingRepository listingRepository;
    private final ListingViewEventRepository listingViewEventRepository;
    private final FavoriteRepository favoriteRepository;
    private final ViewingRequestRepository viewingRequestRepository;

    public OwnerStatsService(ListingRepository listingRepository,
                             ListingViewEventRepository listingViewEventRepository,
                             FavoriteRepository favoriteRepository,
                             ViewingRequestRepository viewingRequestRepository) {
        this.listingRepository = listingRepository;
        this.listingViewEventRepository = listingViewEventRepository;
        this.favoriteRepository = favoriteRepository;
        this.viewingRequestRepository = viewingRequestRepository;
    }

    @Override
    public OwnerStats getByOwner(Long ownerId) {
        long activeListings        = countActiveListings(ownerId);
        long savedByUsers          = favoriteRepository.countByListingOwnerId(ownerId);
        long pendingViewingRequests = countPendingViewingRequests(ownerId);
        long totalViews            = listingViewEventRepository.countByOwnerId(ownerId);
        List<Long> viewsTrend      = getViewsTrend(ownerId);

        return new OwnerStats(activeListings, savedByUsers, pendingViewingRequests, totalViews, viewsTrend);
    }

    private long countActiveListings(Long ownerId) {
        return listingRepository.findByOwnerId(ownerId).stream()
                .filter(listing -> ListingStatus.ACTIVE.equals(listing.getStatus()))
                .count();
    }

    private long countPendingViewingRequests(Long ownerId) {
        return viewingRequestRepository.findByOwnerId(ownerId).stream()
                .filter(viewingRequest -> ViewingStatus.PENDING.equals(viewingRequest.getStatus()))
                .count();
    }

    private List<Long> getViewsTrend(Long ownerId) {
        LocalDate startDate = LocalDate.now().minusDays(TREND_DAYS - 1);
        Date since = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<ListingViewEvent> events = listingViewEventRepository.findByOwnerIdAndViewedAtAfter(ownerId, since);

        Map<LocalDate, Long> countsByDate = events.stream()
                .collect(Collectors.groupingBy(
                        event -> event.getViewedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        Collectors.counting()
                ));

        List<Long> trend = new ArrayList<>(TREND_DAYS);
        for (int i = 0; i < TREND_DAYS; i++) {
            trend.add(countsByDate.getOrDefault(startDate.plusDays(i), 0L));
        }
        return trend;
    }
}

package sk.fsa.rental.domain;

import java.util.List;

public record OwnerStats(
        long activeListings,
        long savedByUsers,
        long pendingViewingRequests,
        long totalViews,
        List<Long> viewsTrend
) {}

package sk.fsa.rental.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import sk.fsa.rental.domain.OwnerStats;
import sk.fsa.rental.domain.User;
import sk.fsa.rental.domain.facade.OwnerStatsFacade;
import sk.fsa.rental.rest.api.OwnerApi;
import sk.fsa.rental.rest.dto.OwnerStatsDto;
import sk.fsa.rental.security.CurrentUserDetailService;

@RestController
public class OwnerStatsRestController implements OwnerApi {

    private final OwnerStatsFacade ownerStatsFacade;
    private final CurrentUserDetailService currentUserDetailService;

    public OwnerStatsRestController(OwnerStatsFacade ownerStatsFacade,
                                    CurrentUserDetailService currentUserDetailService) {
        this.ownerStatsFacade = ownerStatsFacade;
        this.currentUserDetailService = currentUserDetailService;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<OwnerStatsDto> getOwnerStats() {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        OwnerStats stats = ownerStatsFacade.getByOwner(currentUser.getId());
        return ResponseEntity.ok(toDto(stats));
    }

    private OwnerStatsDto toDto(OwnerStats stats) {
        return new OwnerStatsDto()
                .activeListings(stats.activeListings())
                .savedByUsers(stats.savedByUsers())
                .pendingViewingRequests(stats.pendingViewingRequests())
                .totalViews(stats.totalViews())
                .viewsTrend(stats.viewsTrend());
    }
}

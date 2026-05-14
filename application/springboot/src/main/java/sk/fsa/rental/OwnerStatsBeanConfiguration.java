package sk.fsa.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.fsa.rental.domain.facade.OwnerStatsFacade;
import sk.fsa.rental.domain.repository.FavoriteRepository;
import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.repository.ListingViewEventRepository;
import sk.fsa.rental.domain.repository.ViewingRequestRepository;
import sk.fsa.rental.domain.service.OwnerStatsService;

@Configuration
public class OwnerStatsBeanConfiguration {

    @Bean
    public OwnerStatsFacade ownerStatsFacade(ListingRepository listingRepository,
                                             ListingViewEventRepository listingViewEventRepository,
                                             FavoriteRepository favoriteRepository,
                                             ViewingRequestRepository viewingRequestRepository) {
        return new OwnerStatsService(listingRepository, listingViewEventRepository,
                favoriteRepository, viewingRequestRepository);
    }
}

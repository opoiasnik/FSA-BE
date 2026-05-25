package sk.fsa.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.fsa.rental.domain.ListingFactory;
import sk.fsa.rental.domain.PhotoFactory;
import sk.fsa.rental.domain.facade.FavoriteFacade;
import sk.fsa.rental.domain.facade.ListingFacade;
import sk.fsa.rental.domain.repository.FavoriteRepository;
import sk.fsa.rental.domain.repository.ConversationRepository;
import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.repository.ListingViewEventRepository;
import sk.fsa.rental.domain.service.FavoriteService;
import sk.fsa.rental.domain.service.GeocodingService;
import sk.fsa.rental.domain.service.ListingService;

@Configuration
public class ListingBeanConfiguration {

    @Bean
    public ListingFactory listingFactory(ListingRepository listingRepository, GeocodingService geocodingService) {
        return new ListingFactory(listingRepository, geocodingService);
    }

    @Bean
    public ListingFacade listingFacade(ListingRepository listingRepository,
                                       ConversationRepository conversationRepository,
                                       ListingFactory listingFactory,
                                       ListingViewEventRepository listingViewEventRepository,
                                       PhotoFactory photoFactory) {
        return new ListingService(listingRepository, conversationRepository,
                listingFactory, listingViewEventRepository, photoFactory);
    }

    @Bean
    public FavoriteFacade favoriteFacade(FavoriteRepository favoriteRepository,
                                         ListingRepository listingRepository) {
        return new FavoriteService(favoriteRepository, listingRepository);
    }
}

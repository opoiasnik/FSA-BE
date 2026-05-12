package sk.fsa.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.fsa.rental.domain.ListingFactory;
import sk.fsa.rental.domain.facade.FavoriteFacade;
import sk.fsa.rental.domain.facade.ListingFacade;
import sk.fsa.rental.domain.repository.FavoriteRepository;
import sk.fsa.rental.domain.repository.ListingRepository;
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
    public ListingFacade listingFacade(ListingRepository listingRepository, ListingFactory listingFactory) {
        return new ListingService(listingRepository, listingFactory);
    }

    @Bean
    public FavoriteFacade favoriteFacade(FavoriteRepository favoriteRepository,
                                         ListingRepository listingRepository) {
        return new FavoriteService(favoriteRepository, listingRepository);
    }
}

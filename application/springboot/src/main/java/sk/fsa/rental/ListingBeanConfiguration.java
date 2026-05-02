package sk.fsa.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.fsa.rental.domain.FavoriteFactory;
import sk.fsa.rental.domain.ListingFactory;
import sk.fsa.rental.domain.facade.FavoriteFacade;
import sk.fsa.rental.domain.facade.ListingFacade;
import sk.fsa.rental.domain.facade.UserFacade;
import sk.fsa.rental.domain.facade.ViewingRequestFacade;
import sk.fsa.rental.domain.repository.FavoriteRepository;
import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.repository.UserRepository;
import sk.fsa.rental.domain.repository.ViewingRequestRepository;
import sk.fsa.rental.domain.service.FavoriteService;
import sk.fsa.rental.domain.service.GeocodingService;
import sk.fsa.rental.domain.service.ListingService;
import sk.fsa.rental.domain.service.UserService;
import sk.fsa.rental.domain.service.ViewingRequestService;

@Configuration
public class ListingBeanConfiguration {

    @Bean
    public UserFacade userFacade(UserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    public ListingFactory listingFactory(ListingRepository listingRepository, GeocodingService geocodingService) {
        return new ListingFactory(listingRepository, geocodingService);
    }

    @Bean
    public ListingFacade listingFacade(ListingRepository listingRepository, ListingFactory listingFactory) {
        return new ListingService(listingRepository, listingFactory);
    }

    @Bean
    public FavoriteFactory favoriteFactory(FavoriteRepository favoriteRepository) {
        return new FavoriteFactory(favoriteRepository);
    }

    @Bean
    public FavoriteFacade favoriteFacade(FavoriteRepository favoriteRepository,
                                         ListingRepository listingRepository,
                                         FavoriteFactory favoriteFactory) {
        return new FavoriteService(favoriteRepository, listingRepository, favoriteFactory);
    }

    @Bean
    public ViewingRequestFacade viewingRequestFacade(ViewingRequestRepository viewingRequestRepository,
                                                      ListingRepository listingRepository) {
        return new ViewingRequestService(viewingRequestRepository, listingRepository);
    }
}

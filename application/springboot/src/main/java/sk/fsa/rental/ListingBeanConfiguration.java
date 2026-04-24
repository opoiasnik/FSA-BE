package sk.fsa.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.fsa.rental.domain.ListingFactory;
import sk.fsa.rental.domain.facade.ListingFacade;
import sk.fsa.rental.domain.facade.UserFacade;
import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.repository.UserRepository;
import sk.fsa.rental.domain.service.ListingService;
import sk.fsa.rental.domain.service.UserService;

@Configuration
public class ListingBeanConfiguration {

    @Bean
    public UserFacade userFacade(UserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    public ListingFactory listingFactory(ListingRepository listingRepository) {
        return new ListingFactory(listingRepository);
    }

    @Bean
    public ListingFacade listingFacade(ListingRepository listingRepository, ListingFactory listingFactory) {
        return new ListingService(listingRepository, listingFactory);
    }
}

package sk.fsa.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.fsa.rental.domain.facade.ListingFacade;
import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.repository.UserRepository;
import sk.fsa.rental.domain.service.ListingService;

@Configuration
public class ListingBeanConfiguration {

    @Bean
    public ListingFacade listingFacade(ListingRepository listingRepository, UserRepository userRepository) {
        return new ListingService(listingRepository, userRepository);
    }
}

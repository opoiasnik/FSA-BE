package sk.fsa.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.fsa.rental.domain.facade.ViewingRequestFacade;
import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.repository.ViewingRequestRepository;
import sk.fsa.rental.domain.service.ViewingRequestService;

@Configuration
public class ViewingRequestBeanConfiguration {

    @Bean
    public ViewingRequestFacade viewingRequestFacade(ViewingRequestRepository viewingRequestRepository,
                                                     ListingRepository listingRepository) {
        return new ViewingRequestService(viewingRequestRepository, listingRepository);
    }
}

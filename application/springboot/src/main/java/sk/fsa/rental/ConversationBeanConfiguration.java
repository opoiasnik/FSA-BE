package sk.fsa.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.fsa.rental.domain.ConversationFactory;
import sk.fsa.rental.domain.facade.ConversationFacade;
import sk.fsa.rental.domain.repository.ConversationRepository;
import sk.fsa.rental.domain.repository.ListingRepository;
import sk.fsa.rental.domain.service.ConversationService;

@Configuration
public class ConversationBeanConfiguration {

    @Bean
    public ConversationFactory conversationFactory() {
        return new ConversationFactory();
    }

    @Bean
    public ConversationFacade conversationFacade(ConversationRepository conversationRepository,
                                                 ListingRepository listingRepository,
                                                 ConversationFactory conversationFactory) {
        return new ConversationService(conversationRepository, listingRepository, conversationFactory);
    }
}

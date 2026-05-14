package sk.fsa.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.fsa.rental.domain.PhotoFactory;
import sk.fsa.rental.domain.facade.PhotoFacade;
import sk.fsa.rental.domain.repository.PhotoRepository;
import sk.fsa.rental.domain.service.PhotoService;

@Configuration
public class PhotoBeanConfiguration {

    @Bean
    public PhotoFactory photoFactory() {
        return new PhotoFactory();
    }

    @Bean
    public PhotoFacade photoFacade(PhotoRepository photoRepository) {
        return new PhotoService(photoRepository);
    }
}

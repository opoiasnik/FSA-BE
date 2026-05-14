package sk.fsa.rental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.fsa.rental.domain.PhotoFactory;
import sk.fsa.rental.domain.facade.UserFacade;
import sk.fsa.rental.domain.repository.PhotoRepository;
import sk.fsa.rental.domain.repository.UserRepository;
import sk.fsa.rental.domain.service.UserService;

@Configuration
public class UserBeanConfiguration {

    @Bean
    public UserFacade userFacade(UserRepository userRepository, PhotoRepository photoRepository,
                                 PhotoFactory photoFactory) {
        return new UserService(userRepository, photoRepository, photoFactory);
    }
}

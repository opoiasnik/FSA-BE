package sk.fsa.rental.geocoding;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
class NominatimRestClientConfiguration {

    private static final String BASE_URL = "https://nominatim.openstreetmap.org";
    private static final String USER_AGENT = "fsa-rental-app/1.0 (academic project)";

    @Bean
    RestClient nominatimRestClient() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(3));
        requestFactory.setReadTimeout(Duration.ofSeconds(5));

        return RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("User-Agent", USER_AGENT)
                .requestFactory(requestFactory)
                .build();
    }
}

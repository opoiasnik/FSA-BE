package sk.fsa.rental.geocoding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import sk.fsa.rental.domain.Address;
import sk.fsa.rental.domain.Coordinates;
import sk.fsa.rental.domain.service.GeocodingService;

import java.util.Optional;

@Component
public class NominatimGeocodingAdapter implements GeocodingService {

    private static final Logger LOG = LoggerFactory.getLogger(NominatimGeocodingAdapter.class);

    private final RestClient nominatimRestClient;
    private final NominatimAddressMatcher addressMatcher;

    public NominatimGeocodingAdapter(RestClient nominatimRestClient, NominatimAddressMatcher addressMatcher) {
        this.nominatimRestClient = nominatimRestClient;
        this.addressMatcher = addressMatcher;
    }

    @Override
    public Optional<Coordinates> geocode(Address address) {
        if (address == null) {
            return Optional.empty();
        }
        String query = buildQuery(address);
        try {
            NominatimResult[] results = nominatimRestClient.get()
                    .uri(uri -> uri.path("/search")
                            .queryParam("q", query)
                            .queryParam("format", "json")
                            .queryParam("addressdetails", "1")
                            .queryParam("limit", "1")
                            .build())
                    .retrieve()
                    .body(NominatimResult[].class);

            if (results == null || results.length == 0) {
                LOG.warn("Nominatim returned no results for query: {}", query);
                return Optional.empty();
            }

            NominatimResult first = results[0];
            if (!addressMatcher.matches(address, first.address())) {
                LOG.warn("Nominatim result does not match requested address. Query: {}, result: {}", query, first.displayName());
                return Optional.empty();
            }

            return Optional.of(new Coordinates(
                    Double.parseDouble(first.lat()),
                    Double.parseDouble(first.lon())));
        } catch (Exception e) {
            LOG.warn("Geocoding failed for query '{}': {}", query, e.getMessage());
            return Optional.empty();
        }
    }

    private String buildQuery(Address address) {
        StringBuilder sb = new StringBuilder();
        appendPart(sb, address.getStreet());
        appendPart(sb, address.getCity());
        appendPart(sb, address.getPostalCode());
        appendPart(sb, address.getCountry());
        return sb.toString();
    }

    private void appendPart(StringBuilder sb, String value) {
        if (value == null || value.isBlank()) return;
        if (!sb.isEmpty()) sb.append(", ");
        sb.append(value.trim());
    }
}

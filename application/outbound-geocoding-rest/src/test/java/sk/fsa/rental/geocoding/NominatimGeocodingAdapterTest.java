package sk.fsa.rental.geocoding;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import sk.fsa.rental.domain.Address;
import sk.fsa.rental.domain.Coordinates;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class NominatimGeocodingAdapterTest {

    private MockRestServiceServer server;
    private NominatimGeocodingAdapter adapter;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder()
                .baseUrl("https://nominatim.openstreetmap.org");
        server = MockRestServiceServer.bindTo(builder).build();
        adapter = new NominatimGeocodingAdapter(builder.build(), new NominatimAddressMatcher());
    }

    @Test
    void geocodeReturnsCoordinatesWhenResolvedAddressMatchesRequest() {
        server.expect(request -> assertTrue(request.getURI().getQuery().contains("addressdetails=1")))
                .andRespond(withSuccess("""
                        [{
                          "lat": "48.7164",
                          "lon": "21.2611",
                          "display_name": "Bukovecka 15, Kosice, Slovakia",
                          "address": {
                            "road": "Bukovecka",
                            "house_number": "15",
                            "city": "Kosice",
                            "postcode": "04001",
                            "country": "Slovakia",
                            "country_code": "sk"
                          }
                        }]
                        """, MediaType.APPLICATION_JSON));

        Optional<Coordinates> result = adapter.geocode(new Address("Bukovecka 15", "Kosice", "04001", "Slovakia"));

        assertTrue(result.isPresent());
        assertEquals(48.7164, result.get().lat());
        assertEquals(21.2611, result.get().lng());
        server.verify();
    }

    @Test
    void geocodeAcceptsKnownStreetWhenHouseNumberOrPostcodeDiffers() {
        server.expect(request -> assertTrue(request.getURI().getQuery().contains("addressdetails=1")))
                .andRespond(withSuccess("""
                        [{
                          "lat": "48.7164",
                          "lon": "21.2611",
                          "display_name": "Bukovecka, Kosice, Slovakia",
                          "address": {
                            "road": "Bukovecka",
                            "city": "Kosice",
                            "postcode": "04001",
                            "country": "Slovakia",
                            "country_code": "sk"
                          }
                        }]
                        """, MediaType.APPLICATION_JSON));

        Optional<Coordinates> result = adapter.geocode(new Address("Bukovecka 8", "Kosice", "04011", "Slovakia"));

        assertTrue(result.isPresent());
        server.verify();
    }

    @Test
    void geocodeReturnsEmptyWhenResolvedStreetDoesNotMatchRequest() {
        server.expect(request -> assertTrue(request.getURI().getQuery().contains("addressdetails=1")))
                .andRespond(withSuccess("""
                        [{
                          "lat": "48.7164",
                          "lon": "21.2611",
                          "display_name": "Bukovecka 15, Kosice, Slovakia",
                          "address": {
                            "road": "Bukovecka",
                            "house_number": "15",
                            "city": "Kosice",
                            "postcode": "04001",
                            "country": "Slovakia",
                            "country_code": "sk"
                          }
                        }]
                        """, MediaType.APPLICATION_JSON));

        Optional<Coordinates> result = adapter.geocode(new Address("Bukifcka 15", "Kosice", "04001", "Slovakia"));

        assertTrue(result.isEmpty());
        server.verify();
    }
}

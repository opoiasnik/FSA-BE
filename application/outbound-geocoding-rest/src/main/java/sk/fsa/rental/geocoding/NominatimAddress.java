package sk.fsa.rental.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
record NominatimAddress(
        String road,
        String pedestrian,
        String residential,
        @JsonProperty("house_number") String houseNumber,
        String city,
        String town,
        String village,
        String municipality,
        String postcode,
        String country,
        @JsonProperty("country_code") String countryCode) {

    String roadName() {
        if (road != null) return road;
        if (pedestrian != null) return pedestrian;
        return residential;
    }

    String cityName() {
        if (city != null) return city;
        if (town != null) return town;
        if (village != null) return village;
        return municipality;
    }
}

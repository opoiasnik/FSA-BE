package sk.fsa.rental.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
record NominatimResult(
        String lat,
        String lon,
        @JsonProperty("display_name") String displayName,
        NominatimAddress address) {
}

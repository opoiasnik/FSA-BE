package sk.fsa.rental.geocoding;

import org.springframework.stereotype.Component;
import sk.fsa.rental.domain.Address;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
class NominatimAddressMatcher {

    private static final Pattern TRAILING_HOUSE_NUMBER =
            Pattern.compile("^(?<road>.+?)\\s+(?<number>\\d+[A-Za-z]?(?:/\\d+[A-Za-z]?)?)$");
    private static final Pattern LEADING_HOUSE_NUMBER =
            Pattern.compile("^(?<number>\\d+[A-Za-z]?(?:/\\d+[A-Za-z]?)?)\\s+(?<road>.+)$");

    boolean matches(Address requested, NominatimAddress resolved) {
        if (requested == null || resolved == null) {
            return false;
        }

        StreetParts street = splitStreet(requested.getStreet());
        return same(street.road(), resolved.roadName())
                && cityMatches(requested.getCity(), resolved.cityName())
                && sameCountry(requested.getCountry(), resolved.country(), resolved.countryCode());
    }

    private StreetParts splitStreet(String street) {
        if (street == null) {
            return new StreetParts("", null);
        }

        String trimmed = street.trim();
        Matcher trailing = TRAILING_HOUSE_NUMBER.matcher(trimmed);
        if (trailing.matches()) {
            return new StreetParts(trailing.group("road"), trailing.group("number"));
        }

        Matcher leading = LEADING_HOUSE_NUMBER.matcher(trimmed);
        if (leading.matches()) {
            return new StreetParts(leading.group("road"), leading.group("number"));
        }

        return new StreetParts(trimmed, null);
    }

    private boolean sameCountry(String expected, String country, String countryCode) {
        String normalized = normalize(expected);
        return normalized.equals(normalize(country))
                || ("slovakia".equals(normalized) && "sk".equals(normalize(countryCode)))
                || ("slovensko".equals(normalized) && "sk".equals(normalize(countryCode)));
    }

    private boolean cityMatches(String expected, String actual) {
        String left = normalize(expected);
        String right = normalize(actual);
        return left.equals(right) || right.contains(left) || left.contains(right);
    }

    private boolean same(String expected, String actual) {
        return normalize(expected).equals(normalize(actual));
    }

    private String normalize(String value) {
        if (value == null) {
            return "";
        }
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", " ")
                .trim();
        return normalized.replaceAll("\\s+", " ");
    }

    private record StreetParts(String road, String houseNumber) {
    }
}

package sk.fsa.rental.domain;

public class Address {
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private String district;
    private String region;
    private Double lat;
    private Double lng;

    public Address() {
    }

    public Address(String street, String city, String postalCode, String country) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public Address(String street, String city, String postalCode, String country,
                   String district, String region, Double lat, Double lng) {
        this(street, city, postalCode, country);
        this.district = district;
        this.region = region;
        this.lat = lat;
        this.lng = lng;
    }

    public void validate() {
        requireField(street != null && !street.trim().isEmpty(),
                "address.street", "Street is required.");
        requireField(city != null && !city.trim().isEmpty(),
                "address.city", "City is required.");
        requireField(postalCode != null && !postalCode.trim().isEmpty(),
                "address.postalCode", "Postal code is required.");
        requireField(country != null && !country.trim().isEmpty(),
                "address.country", "Country is required.");
    }

    public void assignCoordinates(Coordinates coordinates) {
        requireField(coordinates != null,
                "address.street", "Address could not be verified. Check street, city, postal code and country.");
        this.lat = coordinates.lat();
        this.lng = coordinates.lng();
    }

    private void requireField(boolean condition, String field, String message) {
        if (!condition) {
            throw new RentalException(RentalException.Type.VALIDATION, message, field);
        }
    }

    public String getStreet() {
        return street;
    }

    void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    void setCountry(String country) {
        this.country = country;
    }

    public String getDistrict() { return district; }
    void setDistrict(String district) { this.district = district; }

    public String getRegion() { return region; }
    void setRegion(String region) { this.region = region; }

    public Double getLat() { return lat; }
    void setLat(Double lat) { this.lat = lat; }

    public Double getLng() { return lng; }
    void setLng(Double lng) { this.lng = lng; }
}

package sk.fsa.rental.domain;

public class Address {
    private String street;
    private String city;
    private String postalCode;
    private String country;

    public Address() {
    }

    public Address(String street, String city, String postalCode, String country) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public void validate() {
        require(street != null && !street.trim().isEmpty(),
                "Street is required.");
        require(city != null && !city.trim().isEmpty(),
                "City is required.");
        require(postalCode != null && !postalCode.trim().isEmpty(),
                "Postal code is required.");
        require(country != null && !country.trim().isEmpty(),
                "Country is required.");
    }

    private void require(boolean condition, String message) {
        if (!condition) {
            throw new RentalException(RentalException.Type.VALIDATION, message);
        }
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

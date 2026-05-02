package sk.fsa.rental.domain.service;

import sk.fsa.rental.domain.Address;
import sk.fsa.rental.domain.Coordinates;

import java.util.Optional;

public interface GeocodingService {

    Optional<Coordinates> geocode(Address address);
}

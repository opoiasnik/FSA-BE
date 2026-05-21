package sk.fsa.rental.domain;

import java.time.Year;

public class PropertyFeatures {
    private PropertyType propertyType;
    private Double area;
    private Integer roomCount;
    private Integer floor;
    private Boolean furnished;
    private Boolean parkingAvailable;
    private Boolean balcony;
    private Boolean elevator;
    private Boolean petsAllowed;
    private String energyClass;
    private Integer yearBuilt;

    public PropertyFeatures() {
    }

    public PropertyFeatures(PropertyType propertyType, Double area, Integer roomCount, Integer floor,
                            Boolean furnished, Boolean parkingAvailable, Boolean balcony,
                            Boolean elevator, Boolean petsAllowed, String energyClass, Integer yearBuilt) {
        this.propertyType = propertyType;
        this.area = area;
        this.roomCount = roomCount;
        this.floor = floor;
        this.furnished = furnished;
        this.parkingAvailable = parkingAvailable;
        this.balcony = balcony;
        this.elevator = elevator;
        this.petsAllowed = petsAllowed;
        this.energyClass = energyClass;
        this.yearBuilt = yearBuilt;
    }

    public void validate() {
        require(propertyType != null, "Property type is required.");
        if (area != null) {
            require(area > 0, "Area must be positive.");
        }
        if (roomCount != null) {
            require(roomCount > 0, "Room count must be positive.");
        }
        if (yearBuilt != null) {
            require(yearBuilt >= 1800 && yearBuilt <= Year.now().getValue() + 1,
                    "Year built must be realistic.");
        }
    }

    private void require(boolean condition, String message) {
        if (!condition) {
            throw new RentalException(RentalException.Type.VALIDATION, message);
        }
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public Double getArea() {
        return area;
    }

    void setArea(Double area) {
        this.area = area;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public Integer getFloor() {
        return floor;
    }

    void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Boolean getFurnished() {
        return furnished;
    }

    void setFurnished(Boolean furnished) {
        this.furnished = furnished;
    }

    public Boolean getParkingAvailable() {
        return parkingAvailable;
    }

    void setParkingAvailable(Boolean parkingAvailable) {
        this.parkingAvailable = parkingAvailable;
    }

    public Boolean getBalcony() { return balcony; }
    void setBalcony(Boolean balcony) { this.balcony = balcony; }

    public Boolean getElevator() { return elevator; }
    void setElevator(Boolean elevator) { this.elevator = elevator; }

    public Boolean getPetsAllowed() { return petsAllowed; }
    void setPetsAllowed(Boolean petsAllowed) { this.petsAllowed = petsAllowed; }

    public String getEnergyClass() { return energyClass; }
    void setEnergyClass(String energyClass) { this.energyClass = energyClass; }

    public Integer getYearBuilt() { return yearBuilt; }
    void setYearBuilt(Integer yearBuilt) { this.yearBuilt = yearBuilt; }
}

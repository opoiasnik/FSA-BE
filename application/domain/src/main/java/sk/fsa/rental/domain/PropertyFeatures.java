package sk.fsa.rental.domain;

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

    public void validate() {
        require(propertyType != null, "Property type is required.");
        if (area != null) {
            require(area > 0, "Area must be positive.");
        }
        if (roomCount != null) {
            require(roomCount > 0, "Room count must be positive.");
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

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Boolean getFurnished() {
        return furnished;
    }

    public void setFurnished(Boolean furnished) {
        this.furnished = furnished;
    }

    public Boolean getParkingAvailable() {
        return parkingAvailable;
    }

    public void setParkingAvailable(Boolean parkingAvailable) {
        this.parkingAvailable = parkingAvailable;
    }

    public Boolean getBalcony() { return balcony; }
    public void setBalcony(Boolean balcony) { this.balcony = balcony; }

    public Boolean getElevator() { return elevator; }
    public void setElevator(Boolean elevator) { this.elevator = elevator; }

    public Boolean getPetsAllowed() { return petsAllowed; }
    public void setPetsAllowed(Boolean petsAllowed) { this.petsAllowed = petsAllowed; }

    public String getEnergyClass() { return energyClass; }
    public void setEnergyClass(String energyClass) { this.energyClass = energyClass; }

    public Integer getYearBuilt() { return yearBuilt; }
    public void setYearBuilt(Integer yearBuilt) { this.yearBuilt = yearBuilt; }
}

package com.example.weather247.locationcard;

public class LocationCardModel {
    private final String region;
    private final String country;
    private final String dateAdded;

    public LocationCardModel(String region, String country, String dateAdded) {
        this.region = region;
        this.country = country;
        this.dateAdded = dateAdded;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getDateAdded() {
        return dateAdded;
    }

}

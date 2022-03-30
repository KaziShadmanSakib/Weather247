package com.example.weather247.location;

public class LocationCardModel {
    private String searchedLocation;
    private String dateAdded;

    public LocationCardModel(String searchedLocation, String dateAdded) {
        this.searchedLocation = searchedLocation;
        this.dateAdded = dateAdded;
    }

    public String getSearchedLocation() {
        return searchedLocation;
    }

    public void setSearchedLocation(String searchedLocation) {
        this.searchedLocation = searchedLocation;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

}

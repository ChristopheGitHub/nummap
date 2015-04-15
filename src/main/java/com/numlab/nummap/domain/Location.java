package com.numlab.nummap.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by christo on 24/03/15.
 */
public class Location {
    private double latitude;
    private double longitude;

    @JsonCreator
    public Location(
        @JsonProperty("latitude") double latitude,
        @JsonProperty("longitude") double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Location{" +
            "latitude=" + latitude +
            ", longitude=" + longitude +
            '}';
    }
}

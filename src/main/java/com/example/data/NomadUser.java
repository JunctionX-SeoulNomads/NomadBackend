package com.example.data;

import org.jetbrains.annotations.NotNull;

public class NomadUser {
    private String userId;
    private double longitude;
    private double latitude;

    public NomadUser() {
        userId = "default";
        longitude = 0.;
        latitude = 0.;
    }

    public NomadUser(@NotNull final String userId, double longitude, double latitude) {
        this.userId = userId;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Coordinate getCoordinates() {
        return new Coordinate(longitude, latitude);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}

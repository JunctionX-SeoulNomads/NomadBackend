package com.example.data;

import org.jetbrains.annotations.NotNull;

/**
 * Class for representing and working with geographic coordinates
 */
public class Coordinate {
    private double longitude;
    private double latitude;

    Coordinate() {
        longitude = 0.;
        latitude = 0.;
    }

    Coordinate(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /** Calculates the distance between this and target points*/
    public double calcDistanceToPoint(@NotNull final Coordinate target) {
        double distance = 0.;
        distance += (this.latitude - target.latitude) * (this.latitude - target.latitude);
        distance += (this.longitude - target.longitude) * (this.longitude - target.longitude);
        //TODO add sqrt
        //distance = Math.sqrt(distance);
        return distance;
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

package com.example.data;

import com.google.inject.internal.cglib.proxy.$Dispatcher;
import org.jetbrains.annotations.NotNull;

public class UserComplain {
    private String userId;
    private String message;
    private double longitude;
    private double latitude;

    public UserComplain() {
        userId = "";
        longitude = 0;
        latitude = 0;
        message = "";
    }

    public UserComplain(@NotNull final String userId,
                        @NotNull final String message,
                        final double longitude,
                        final double latitude) {
        this.userId = userId;
        this.message = message;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Coordinate getCoordinates() {
        return new Coordinate(longitude, latitude);
    }
}

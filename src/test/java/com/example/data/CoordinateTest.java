package com.example.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoordinateTest {
    private double EPS = 0.0001;

    @Test
    public void calculatedCorrectDistanceToTargetPoint() {
        final double startX = 10.;
        final double startY = 100.;
        final double finishX = 70.;
        final double finishY = 30.;
        final double distance = ((startX - finishX) * (startX - finishX)) + ((startY - finishY) * (startY - finishY));

        final Coordinate startPoint = new Coordinate(startX, startY);
        final Coordinate finishPoint = new Coordinate(finishX, finishY);
        final double startFinishDistance = startPoint.calcDistanceToPoint(finishPoint);
        final double finishStartDistance = finishPoint.calcDistanceToPoint(startPoint);

        assertTrue(distance - startFinishDistance < EPS);
        assertTrue(distance - finishStartDistance < EPS);
    }

    @Test
    public void defaultCoordinatesIsZero() {
        final Coordinate coordinate = new Coordinate();

        assertEquals(0, coordinate.getLatitude());
        assertEquals(0, coordinate.getLongitude());
    }

    @Test
    public void canCreateUserWithCoordinates() {
        final double latitude = 246.34;
        final double longitude = 22.436;

        final Coordinate coordinate = new Coordinate(longitude, latitude);

        assertEquals(latitude, coordinate.getLatitude());
        assertEquals(longitude, coordinate.getLongitude());
    }
}

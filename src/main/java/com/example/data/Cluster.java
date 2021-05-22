package com.example.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Cluster describe single logically divided geographic area
 * It's represents throw it's coordinates
 */
public class Cluster {
    private AtomicInteger counter; // keep statistics about cluster
    private ConcurrentLinkedDeque<Coordinate> identificationCoordinates;

    Cluster() {
        counter = new AtomicInteger(0);
        identificationCoordinates = new ConcurrentLinkedDeque<>();
    }

    Cluster(@NotNull final AtomicInteger counter, @NotNull final List<Coordinate> coordinates) {
        this.counter = new AtomicInteger(counter.get());
        identificationCoordinates = new ConcurrentLinkedDeque<>();
        identificationCoordinates.addAll(coordinates);
    }


    public void addIdentificationPoint(@NotNull final Coordinate coordinate) {
        identificationCoordinates.add(coordinate);
    }

    /** Calculate the distance between this cluster and giver targetPoint using identifications points of that cluster*/
    public double getDistanceToTargetPoint(@NotNull final Coordinate targetPoint) {
        if (identificationCoordinates.isEmpty()) {
            return -1;
        }

        double minimumDistance = identificationCoordinates.getFirst().calcDistanceToPoint(targetPoint);
        for (final Coordinate identificationPoint : identificationCoordinates) {
            final double currentDistance = identificationPoint.calcDistanceToPoint(targetPoint);
            if (currentDistance < minimumDistance) {
                minimumDistance = currentDistance;
            }
        }

        return minimumDistance;
    }

    public void incrementCounter() {
        counter.incrementAndGet();
    }

    public int getCounter() {
        return counter.get();
    }
}

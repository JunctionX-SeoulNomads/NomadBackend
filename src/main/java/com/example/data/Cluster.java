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
    private int clusterId;
    private int monthlyStatistic; // keep monthly statistic
    private AtomicInteger aliveCounter; // keep statistics about cluster
    private AtomicInteger dailyCounter; // keep number of daily visited users
    private Coordinate clusterCoordinates;

    Cluster() {
        clusterId = 0;
        aliveCounter = new AtomicInteger(0);
        dailyCounter = new AtomicInteger(0);
        monthlyStatistic = 0;
        clusterCoordinates = new Coordinate();
    }

    Cluster(final int clusterId,
            final int monthlyStatistic,
            @NotNull final AtomicInteger aliveCounter,
            @NotNull final AtomicInteger dailyCounter,
            @NotNull final Coordinate clusterCoordinates) {
        this.clusterId = clusterId;
        this.monthlyStatistic = monthlyStatistic;
        this.aliveCounter = new AtomicInteger(aliveCounter.get());
        this.dailyCounter = new AtomicInteger(dailyCounter.get());
        this.clusterCoordinates = new Coordinate(clusterCoordinates.getLongitude(), clusterCoordinates.getLatitude());
    }

    /** Calculate the distance between this cluster and giver targetPoint using identifications points of that cluster*/
    public double getDistanceToTargetPoint(@NotNull final Coordinate targetPoint) {
        return clusterCoordinates.calcDistanceToPoint(targetPoint);
    }

    public Cluster incrementAliveCounter() {
        aliveCounter.incrementAndGet();
        dailyCounter.incrementAndGet();
        return this;
    }

    public Cluster decrementAliveCounter() {
        aliveCounter.decrementAndGet();
        return this;
    }

    public int getAliveCounter() {
        return aliveCounter.get();
    }

    public int getMonthlyStatistic() { return monthlyStatistic; }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }
}

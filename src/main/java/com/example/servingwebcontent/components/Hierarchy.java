package com.example.servingwebcontent.components;

import com.example.data.Cluster;
import com.example.data.Coordinate;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class which will be used for accessing and updating clusters data
 */
@Component
public class Hierarchy {
    private final List<Cluster> clustersList;

    Hierarchy() {
        clustersList = new ArrayList<>();
    }

    /** load information about clusters from database */
    public Hierarchy loadClustersInfoFromDatabase() {
        return this;
    }

    public Hierarchy saveClustersInfoToDatabase() {
        return this;
    }

    public boolean hasCluster() {
        return (!clustersList.isEmpty());
    }

    public Cluster getNearestCluster(final @NotNull Coordinate coordinate) {
        if (clustersList.isEmpty()) {
            return null;
        }

        Cluster nearestCluster = clustersList.get(0);
        double minDistance = nearestCluster.getDistanceToTargetPoint(coordinate);
        for (int i = 1; i < clustersList.size(); i++) {
            final Cluster curCluster = clustersList.get(i);
            final double distanceToCurCluster = curCluster.getDistanceToTargetPoint(coordinate);

            if (distanceToCurCluster < minDistance) {
                minDistance = distanceToCurCluster;
                nearestCluster = curCluster;
            }
        }

        return nearestCluster;
    }
}

package com.example.servingwebcontent.components;

import com.example.data.Cluster;
import com.example.data.Coordinate;
import com.example.data.UserComplain;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton class which will be used for accessing and updating clusters data
 */
@Component
public class Hierarchy {
    private final List<Cluster> clustersList;
    private final Map<String, Integer> clusterIdByUserId;
    private final Map<Integer, Cluster> clusterById;

    Hierarchy() {
        clustersList = new ArrayList<>();
        clusterIdByUserId = new HashMap<>();
        clusterById = new HashMap<>();
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

    /** returns nearest cluster to given coordinates */
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

    public int getClusterIdByUserId(@NotNull final String userId) {
        return clusterIdByUserId.getOrDefault(userId, -1);
    }

    public Cluster getClusterById(@NotNull final int clusterId) {
        return clusterById.getOrDefault(clusterId, null);
    }

    public Hierarchy setUserClusterIdByUserId(@NotNull final String userId, final int clusterId) {
        clusterIdByUserId.put(userId, clusterId);
        return this;
    }

    /** method for saving complains */
    public Hierarchy saveComplain(@NotNull final UserComplain complain, @NotNull final Cluster nearestCluster) {
        //TODO implement complain saving
        return this;
    }
}

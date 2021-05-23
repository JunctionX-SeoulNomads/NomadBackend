package com.example.servingwebcontent.components;

import com.example.data.*;
import com.example.database.NomadDB;
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
    private final AdvancedSearchAlgorithm advancedSearchAlgorithm;

    Hierarchy() {
        clustersList = new ArrayList<>();
        clusterIdByUserId = new HashMap<>();
        clusterById = new HashMap<>();
        loadClustersInfoFromDatabase();

        List<SimpleCluster> simpleClusterList = new ArrayList<>();
        for (Cluster cluster : clustersList) {
            simpleClusterList.add(cluster.getSimpleCluster());
            clusterById.putIfAbsent(cluster.getClusterId(), cluster);
        }
        advancedSearchAlgorithm = new AdvancedSearchAlgorithm(simpleClusterList);
    }

    /** load information about clusters from database */
    public Hierarchy loadClustersInfoFromDatabase() {
        NomadDB db = new NomadDB();
        List<Cluster> clusters = db.getClustersFromDB();
        for (int i = 0; i < clusters.size(); i++) {
            clustersList.add(clusters.get(i));
            clusterById.put(clusters.get(i).getClusterId(), clusters.get(i));
        }
        System.out.println("Hierarchy::: loaded " + clusters.size() + " from remote database");
        return this;
    }

    public Map<Integer, Integer> getAndResetClustersDailyCounter() {
        Map<Integer, Integer> dailyCounter = new HashMap<>();
        for (Cluster cluster : clustersList) {
            int clusterID = cluster.getClusterId();
            int counter = cluster.getAndResetDailyCounter();
            dailyCounter.put(clusterID, counter);
        }
        return dailyCounter;
    }

    public Map<Integer, Integer> getClustersAliveCounter() {
        Map<Integer, Integer> dailyCounter = new HashMap<>();
        for (Cluster cluster : clustersList) {
            int clusterID = cluster.getClusterId();
            int counter = cluster.getAliveCounter();
            dailyCounter.put(clusterID, counter);
        }
        return dailyCounter;
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
        int nativeSearch = getNearestClusterIdNative(coordinate);
        //int advancedSearch = getNearestClusterIdAdvancedAlgorithm(coordinate);
        return clusterById.get(nativeSearch);
    }

    /** returns nearest cluster id to given coordinates by advanced search algorithm */
    public int getNearestClusterIdAdvancedAlgorithm(final @NotNull Coordinate coordinate) {
        if (clustersList.isEmpty()) {
            return 0;
        }
        return advancedSearchAlgorithm.getNearestCluster(coordinate);
    }

    /** returns nearest cluster id to given coordinates by native algorithm */
    public int getNearestClusterIdNative(final @NotNull Coordinate coordinate) {
        if (clustersList.isEmpty()) {
            return 0;
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

        return nearestCluster.getClusterId();
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

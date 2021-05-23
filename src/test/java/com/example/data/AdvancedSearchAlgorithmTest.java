package com.example.data;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AdvancedSearchAlgorithmTest {

    //@Test
    public void canSearchCorrectly() {
        List<SimpleCluster> clusters = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= 100; i++) {
            int clusterId = i;
            double longitude = random.nextInt() + random.nextDouble();
            double latitude = random.nextInt() + random.nextDouble();
            clusters.add(new SimpleCluster(clusterId, new Coordinate(longitude, latitude)));
        }

        AdvancedSearchAlgorithm advancedSearchAlgorithm = new AdvancedSearchAlgorithm(clusters);

        for (int i = 1; i <= 100; i++) {
            System.out.println(" i = " + i );
            double longitude = random.nextInt() + random.nextDouble();
            double latitude = random.nextInt() + random.nextDouble();

            Coordinate targetPoint = new Coordinate(longitude, latitude);

            int nativeSearchAns = nativeSearch(clusters, targetPoint);
            int advancedSearch = advancedSearchAlgorithm.getNearestCluster(targetPoint);

            assertEquals(nativeSearchAns, advancedSearch);
        }
    }

    private int nativeSearch(List<SimpleCluster> clustersList, Coordinate coordinate) {
        int nearestClusterId = clustersList.get(0).getClusterId();
        double minDistance = clustersList.get(0).getClusterCoordinates().calcDistanceToPoint(coordinate);
        for (int i = 1; i < clustersList.size(); i++) {
            final SimpleCluster cluster = clustersList.get(i);
            final int curClusterId = cluster.getClusterId();
            final double distanceToCurCluster = cluster.getClusterCoordinates().calcDistanceToPoint(coordinate);

            if (distanceToCurCluster < minDistance) {
                minDistance = distanceToCurCluster;
                nearestClusterId = curClusterId;
            }
        }

        return nearestClusterId;
    }
}

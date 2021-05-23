package com.example.data;

import com.example.utils.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class AdvancedSearchAlgorithm {
    Map<Integer, Coordinate> coordinateByClusterId;
    private List<Pair<Integer, List<Pair<Double, Integer>>>> sortedByX;

    public AdvancedSearchAlgorithm(List<SimpleCluster> clustersList) {
        coordinateByClusterId = new HashMap<>();
        for (SimpleCluster cluster : clustersList) {
            coordinateByClusterId.put(cluster.getClusterId(), cluster.getClusterCoordinates());
        }

        sortedByX = new ArrayList<>();

        Map<Integer, List<SimpleCluster>> clus = new HashMap<>();
        for (SimpleCluster cluster : clustersList) {
            int x = (int) cluster.getClusterCoordinates().getLongitude();
            clus.putIfAbsent(x, new ArrayList<>());
            clus.get(x).add(cluster);
        }

        for (Integer x : clus.keySet()) {
            List<Pair<Double, Integer>> sortedByY = new ArrayList<>();
            for (SimpleCluster cluster : clus.get(x)) {
                Double y = cluster.getClusterCoordinates().getLatitude();
                int clusterId = cluster.getClusterId();
                sortedByY.add(new Pair<>(y, clusterId));
            }
            sortedByY.sort(new Comparator<Pair<Double, Integer>>() {
                @Override
                public int compare(Pair<Double, Integer> o1, Pair<Double, Integer> o2) {
                    return o1.getFirst().compareTo(o2.getFirst());
                }
            });
            sortedByX.add(new Pair<>(x, sortedByY));
        }

        sortedByX.sort(new Comparator<Pair<Integer, List<Pair<Double, Integer>>>>() {
            @Override
            public int compare(Pair<Integer, List<Pair<Double, Integer>>> o1, Pair<Integer, List<Pair<Double, Integer>>> o2) {
                return o1.getFirst().compareTo(o2.getFirst());
            }
        });
    }

    public int getNearestCluster(@NotNull final Coordinate coordinate) {
        List<Pair<Coordinate, Integer>> answerCandidates = new ArrayList<>();

        int left = 0;
        int right = sortedByX.size() - 1;
        int targetX = (int) coordinate.getLongitude();
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (sortedByX.get(mid).getFirst() < targetX) {
                left = mid;
            } else {
                right = mid;
            }
        }

        System.out.println("left = " + left);
        for (Pair<Double, Integer> pair : sortedByX.get(left).getSecond()) {
            int clusterId = pair.getSecond();
            answerCandidates.add(new Pair<>(coordinateByClusterId.get(clusterId), clusterId));
        }

        /*
        double x = coordinateByClusterId.get(sortedByX.get(xId1).getSecond().get(left).getSecond()).getLongitude();
        Double y = sortedByX.get(xId1).getSecond().get(left).getFirst();
        int clusterId = sortedByX.get(xId1).getSecond().get(left).getSecond();
        answerCandidates.add(new Pair<>(new Coordinate(x, y), clusterId));

        x = coordinateByClusterId.get(sortedByX.get(xId1).getSecond().get(left).getSecond()).getLongitude();
        y = sortedByX.get(xId1).getSecond().get(right).getFirst();
        clusterId = sortedByX.get(xId1).getSecond().get(right).getSecond();
        answerCandidates.add(new Pair<>(new Coordinate(x, y), clusterId));


        //find best in second X
        left = 0;
        right = sortedByX.get(xId2).getSecond().size() - 1;
        targetY = (int) coordinate.getLatitude();
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            double midVal = sortedByX.get(xId2).getSecond().get(mid).getFirst();
            if (midVal < targetY) {
                left = mid;
            } else {
                right = mid;
            }
        }

        x = coordinateByClusterId.get(sortedByX.get(xId1).getSecond().get(left).getSecond()).getLongitude();
        y = sortedByX.get(xId2).getSecond().get(right).getFirst();
        clusterId = sortedByX.get(xId2).getSecond().get(right).getSecond();
        answerCandidates.add(new Pair<>(new Coordinate(x, y), clusterId));

        x = coordinateByClusterId.get(sortedByX.get(xId1).getSecond().get(left).getSecond()).getLongitude();
        y = sortedByX.get(xId2).getSecond().get(right).getFirst();
        clusterId = sortedByX.get(xId2).getSecond().get(right).getSecond();
        answerCandidates.add(new Pair<>(new Coordinate(x, y), clusterId));
         */

        // find the nearest cluster among answer candidates (there could be no more than 4 candidates).
        double minDist = coordinate.calcDistanceToPoint(answerCandidates.get(0).getFirst());
        int ansClusterId = answerCandidates.get(0).getSecond();
        for (Pair<Coordinate, Integer> pair : answerCandidates) {
            double curDist = coordinate.calcDistanceToPoint(pair.getFirst());
            int curClusterId = pair.getSecond();
            if (curDist < minDist) {
                minDist = curDist;
                ansClusterId = curClusterId;
            }
        }

        return ansClusterId;
    }
}

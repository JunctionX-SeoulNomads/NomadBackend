package com.example.data;


public class SimpleCluster {
    private int clusterId;
    private Coordinate clusterCoordinates;

    SimpleCluster() {
        clusterId = 0;
        clusterCoordinates = new Coordinate();
    }

    SimpleCluster(int clusterId, Coordinate clusterCoordinates) {
        this.clusterId = clusterId;
        this.clusterCoordinates = new Coordinate(clusterCoordinates.getLongitude(), clusterCoordinates.getLatitude());
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public Coordinate getClusterCoordinates() {
        return clusterCoordinates;
    }

    public void setClusterCoordinates(Coordinate clusterCoordinates) {
        this.clusterCoordinates = clusterCoordinates;
    }
}

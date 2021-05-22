package com.example.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClusterTest {

    @Test
    public void canCreateCluster() {
        final Cluster cluster = new Cluster();

        assertEquals(0, cluster.getCounter());
    }
}

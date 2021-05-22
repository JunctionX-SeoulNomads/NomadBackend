package com.example.database;

import com.example.data.Cluster;
import com.example.data.Coordinate;
import com.example.utils.TokensStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class NomadDB {
    private TokensStorage tokensStorage;
    private DatabaseConnection dbConnection;

    public NomadDB() {
        tokensStorage = new TokensStorage();
        tokensStorage.addTokens();

        final Properties connectionProperties = new Properties();
        connectionProperties.setProperty("url", tokensStorage.getTokens("AWS_DB_W3_URL"));
        connectionProperties.setProperty("user", tokensStorage.getTokens("AWS_DB_W3_ROOT_USER"));
        connectionProperties.setProperty("password", tokensStorage.getTokens("AWS_DB_W3_ROOT_PASSWORD"));

        dbConnection = new DatabaseConnectionAwsImpl(connectionProperties);
        dbConnection.connect();
    }

    public List<Cluster> getClustersFromDB() {
        final List<Cluster> clusters = new ArrayList<>();

        String SQL_SELECT_MONTHLY_STAT = "select cluster_id, sum(count) from SafeNomad.CounterMonthly group by cluster_id";
        Map<Integer, Integer> clusterMonthlyStat = new HashMap<>();

        try {
            ResultSet resultSet = dbConnection.executeSearchQuery(SQL_SELECT_MONTHLY_STAT);

            while (resultSet.next()) {
                int clusterId = resultSet.getInt(1);
                int sum = resultSet.getInt(2);
//                System.out.println("clusterId : " + clusterId);
//                System.out.println("sum of count : " + sum);
//                System.out.println("--");
                clusterMonthlyStat.put(clusterId, sum);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String SQL_SELECT = "SELECT * FROM SafeNomad.Cluster;";
        try {
            ResultSet resultSet = dbConnection.executeSearchQuery(SQL_SELECT);

            while (resultSet.next()) {
                final int clusterId = resultSet.getInt(1);
                final double longitude = resultSet.getDouble(2);
                final double latitude = resultSet.getDouble(3);
                final int monthlyStat = clusterMonthlyStat.getOrDefault(clusterId, 0);
                final Coordinate clusterCenter = new Coordinate(longitude, latitude);
//                System.out.print("clusterId : " + clusterId);
//                System.out.print(" longitude : " + longitude);
//                System.out.print(" latitude : " + latitude);
//                System.out.print(" monthlyStat : " + monthlyStat);
                final Cluster cluster = new Cluster(clusterId, monthlyStat, 0, 0, clusterCenter);
                clusters.add(cluster);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clusters;
    }
}

package com.example.database;

import com.example.data.Cluster;
import com.example.data.Coordinate;
import com.example.utils.TokensStorage;
import org.jetbrains.annotations.NotNull;

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

    public void updateClusterMonthlyStatus(@NotNull final Map<Integer, Integer> dailyActivityByClusterStatus) {
        try {
            Date date = new Date();

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(date);
            for (final Integer clusterId : dailyActivityByClusterStatus.keySet()) {
                final int acitveUsers = dailyActivityByClusterStatus.get(clusterId);
                String SQL_ADD_CLUSTER_RECORD = "insert into SafeNomad.CounterMonthly (cluster_id, count, time) " +
                        "values (" + clusterId + ", " + acitveUsers + ", '" + currentTime + "');";
                System.out.println(SQL_ADD_CLUSTER_RECORD);
                dbConnection.executeQuery(SQL_ADD_CLUSTER_RECORD);
            }

            for (final Integer clusterId : dailyActivityByClusterStatus.keySet()) {
                String SQL_COUNT_CLUSTER_RECORDS = "Select count(*) from SafeNomad.CounterMonthly where cluster_id = " + clusterId + ";";
                ResultSet resultSet = dbConnection.executeSearchQuery(SQL_COUNT_CLUSTER_RECORDS);
                resultSet.next();
                int numberOfClusterRecords = resultSet.getInt(1);
                if (numberOfClusterRecords > 30) {
                    String SQL_GET_OLDEST_RECORD = "Select id from SafeNomad.CounterMonthly where cluster_id = " + clusterId + " order by time limit 1;";
                    resultSet = dbConnection.executeSearchQuery(SQL_COUNT_CLUSTER_RECORDS);
                    resultSet.next();
                    int recordId = resultSet.getInt(1);
                    String SQL_DELETE_RECORD = "DELETE from SafeNomad.CounterMonthly where id = " + recordId + ";";
                    dbConnection.executeQuery(SQL_DELETE_RECORD);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void resetClusterDailyCounter(List<Integer> clustersIdList) {
        for (final Integer clusterId: clustersIdList) {
            String SQL_RESET_COUNTER_TO_0 = "update SafeNomad.CounterDaily set count = 0 where cluster_id = " + clusterId + ";";
            dbConnection.executeQuery(SQL_RESET_COUNTER_TO_0);
        }
    }

    public Map<Integer, Integer> getClusterDailyStat() {
        Map<Integer, Integer> clusterStat = new HashMap<>();

        try {
            String SQL_SELECT = "select * from SafeNomad.CounterDaily;";
            ResultSet resultSet = dbConnection.executeSearchQuery(SQL_SELECT);

            while (resultSet.next()) {
                int clusterId = resultSet.getInt(2);
                int counter = resultSet.getInt(3);
                clusterStat.put(clusterId, counter);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clusterStat;
    }

    public void updateClusterDailyStat(Map<Integer, Integer> dailyStatByClusterId) {

        for (final Integer clusterId: dailyStatByClusterId.keySet()) {
            int counter = dailyStatByClusterId.get(clusterId);
            String SQL_RESET_COUNTER_TO_0 = "update SafeNomad.CounterDaily set count = count + " + counter + " where cluster_id = " + clusterId + ";";
            dbConnection.executeQuery(SQL_RESET_COUNTER_TO_0);
        }
    }

}

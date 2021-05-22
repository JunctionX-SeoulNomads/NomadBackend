package com.example.database;

import com.example.data.Cluster;
import com.example.utils.TokensStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

        String SQL_SELECT_MONTHLY_STAT = "select * from SafeNomad.CounterMonthly;";
        Map<Integer, Integer> clusterMonthlyStat =

        String SQL_SELECT = "SELECT * FROM SafeNomad.Cluster;";

        try {
            ResultSet resultSet = dbConnection.executeSearchQuery(SQL_SELECT);

            while (resultSet.next()) {
                int clusterId = resultSet.getInt(1);
                double longitude = resultSet.getDouble(2);
                double latitude = resultSet.getDouble(3);
                System.out.println("clusterId : " + clusterId);
                System.out.println("longitude : " + longitude);
                System.out.println("latitude : " + latitude);

                Cluster cluster = new Cluster();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clusters;
    }
}

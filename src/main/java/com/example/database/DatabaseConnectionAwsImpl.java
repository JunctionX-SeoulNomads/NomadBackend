package com.example.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionAwsImpl implements DatabaseConnection {
    private Connection connection;
    private final Properties properties;

    public DatabaseConnectionAwsImpl(final @NotNull Properties properties) {
        this.properties = properties;
    }

    @Override
    public boolean connect() {
        try {
            System.out.println("Connecting to the database");
            connection = DriverManager.getConnection(properties.getProperty("url"), properties);
            System.out.println("Database connection COMPLETED test: " + connection.getCatalog());
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Database connection failed ");
        return false;
    }

    @Override
    public boolean executeQuery(final @NotNull String query) {
        System.out.println(" Azure query : " + query);
        try {
            return connection.prepareStatement(query).execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public @Nullable ResultSet executeSearchQuery(@NotNull String query) {
        System.out.println(" Azure query : " + query);
        try {
            return connection.prepareCall(query).executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


}

package com.example.database;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;

public interface DatabaseConnection {

    boolean connect();

    boolean executeQuery(final @NotNull String query);

    @Nullable
    ResultSet executeSearchQuery(final @NotNull String query);

}

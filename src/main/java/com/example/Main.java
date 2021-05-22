package com.example;

import com.example.database.DatabaseConnection;
import com.example.database.DatabaseConnectionAwsImpl;
import com.example.database.NomadDB;
import com.example.utils.TokensStorage;

import java.util.Properties;

public class Main {

    public static void main(String[] args) {

//        TokensStorage tokensStorage = new TokensStorage();
//        tokensStorage.addTokens();
//
//        Properties databaseProperties = new Properties();
//        databaseProperties.setProperty("url", tokensStorage.getTokens("AWS_DB_W3_URL"));
//        databaseProperties.setProperty("user", tokensStorage.getTokens("AWS_DB_W3_ROOT_USER"));
//        databaseProperties.setProperty("password", tokensStorage.getTokens("AWS_DB_W3_ROOT_PASSWORD"));
//
//        DatabaseConnection awsDB = new DatabaseConnectionAwsImpl(databaseProperties);
//        awsDB.connect();

        NomadDB nomadDB = new NomadDB();
        nomadDB.getClustersFromDB();

    }
}

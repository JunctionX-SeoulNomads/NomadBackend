package com.example.utils;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class for storing and accessing tokens
 *
 * Developers : Sabina A, Farrukh Karimov
 * Created : 16.01.2021
 * Modified : 04.02.2021
 * */
public class TokensStorage {

    private String SERVER_PORT = "8080";
    private String AWS_DB_W3_URL = "null";
    private String AWS_DB_W3_ROOT_USER = "null";
    private String AWS_DB_W3_ROOT_PASSWORD = "null";


    /** add parsing tokens from file or System environments */
    public void addTokens() {
        File directory = new File("./");
        String[] flist = directory.list();
        int flag = 0;
        if (flist == null) {
            System.out.println("Empty directory.");
        }
        else {
            for (int i = 0; i < flist.length; i++) {
                String filename = flist[i];
                if (filename.equalsIgnoreCase(".env")) {
                    flag = 1;
                    break;
                }
            }
        }
        if (flag == 1) {
            try(Scanner scanner = new Scanner(new File (".env"))) {
                while (scanner.hasNextLine()) {
                    String str = scanner.nextLine();
                    String buff[] = str.split("=", 2);
                    if (buff.length == 2)
                        setToken(buff[0], buff[1]);
                    else
                        System.out.println("Error with .env file, wrong token format : " + str);
                }

                System.out.println("ENVS: \n" +
                        "SERVER_PORT = " + SERVER_PORT + "\n" +
                        "AWS_DB_W3_URL = " + AWS_DB_W3_URL + "\n" +
                        "AWS_DB_W3_ROOT_USER = " + AWS_DB_W3_ROOT_USER + "\n" +
                        "AWS_DB_W3_ROOT_PASSWORD = " + AWS_DB_W3_ROOT_PASSWORD + "\n\n");
           }
            catch (FileNotFoundException e) {
                System.out.println(" ----------- file not found ------------- ");
                e.printStackTrace();
            }
        }
        else if (flag == 0) {
            SERVER_PORT = System.getenv("PORT");  // heroku gives available port by calling "PORT", not "SERVER_PORT"


            // config vars for connecting to remote database in AmazonWebServices
            AWS_DB_W3_URL = System.getenv("AWS_DB_W3_URL");
            AWS_DB_W3_ROOT_USER = System.getenv("AWS_DB_W3_ROOT_USER");
            AWS_DB_W3_ROOT_PASSWORD = System.getenv("AWS_DB_W3_ROOT_PASSWORD");

        }
    }

    private void setToken(@NotNull final String tokenName, @NotNull final String tokenValue) {
        switch(tokenName) {
            case "PORT" :  // because heroku gives available port by calling "PORT", not "SERVER_PORT"
            case "SERVER_PORT" :
                SERVER_PORT = tokenValue;
                break;
            case "AWS_DB_W3_URL" :
                AWS_DB_W3_URL = tokenValue;
                break;
            case "AWS_DB_W3_ROOT_USER" :
                AWS_DB_W3_ROOT_USER = tokenValue;
                break;
            case "AWS_DB_W3_ROOT_PASSWORD" :
                AWS_DB_W3_ROOT_PASSWORD = tokenValue;
                break;
        }
    }

    /** find and return the needed token */
    public String getTokens(String token) {
        String temp = "null";
        switch (token) {
            case "PORT" : // because heroku gives available port by calling "PORT", not "SERVER_PORT"
            case "SERVER_PORT" :
                temp = SERVER_PORT;
                break;
                case "AWS_DB_W3_URL" :
                temp = AWS_DB_W3_URL;
                break;
            case "AWS_DB_W3_ROOT_USER" :
                temp = AWS_DB_W3_ROOT_USER;
                break;
            case "AWS_DB_W3_ROOT_PASSWORD" :
                temp = AWS_DB_W3_ROOT_PASSWORD;
                break;
        }
        return temp;
    }

    /**Will print values for all tokens that have*/
    public void showTokens() {
        System.out.println("token :::: SERVER_PORT = " + SERVER_PORT);

        System.out.println("token :::: AWS_DB_W3_URL = " + AWS_DB_W3_URL);
        System.out.println("token :::: AWS_DB_W3_ROOT_USER = " + AWS_DB_W3_ROOT_USER);
        System.out.println("token :::: AWS_DB_W3_ROOT_PASSWORD = " + AWS_DB_W3_ROOT_PASSWORD);
    }
}

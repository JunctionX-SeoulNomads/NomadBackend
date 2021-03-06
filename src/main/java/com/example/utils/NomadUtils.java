package com.example.utils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class NomadUtils {
    /** Method to make quick GET request and get the JSON response data*/
    public static String httpsGETRequest(@NotNull final String urlPath) {
        try {
            HttpURLConnection con;
            URL myUrl = new URL(urlPath);
            con = (HttpURLConnection) myUrl.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            StringBuilder content = new StringBuilder();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                br.lines().forEach( line -> {
                    content.append(line);
                    content.append(System.lineSeparator());
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

            return content.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    /** Method to make quick GET request and get the JSON response data*/
    public static String httpsPOSTRequest(@NotNull final String urlPath, byte[] data) {
        try {
            HttpURLConnection con;
            URL myUrl = new URL(urlPath);
            con = (HttpURLConnection) myUrl.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            int length = data.length;

            con.setFixedLengthStreamingMode(length);
            con.connect();
            try(OutputStream os = con.getOutputStream()) {
                os.write(data);
            }
// Do something with http.getInputStream()

            StringBuilder content = new StringBuilder();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                br.lines().forEach( line -> {
                    content.append(line);
                    content.append(System.lineSeparator());
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

            return content.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}

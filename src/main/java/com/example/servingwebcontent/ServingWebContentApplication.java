package com.example.servingwebcontent;

import com.example.NomadUtils;
import com.example.data.Coordinate;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServingWebContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);

        Coordinate coordinate = new Coordinate(23.46, 4544.4444);
        Gson gson = new Gson();
        NomadUtils.httpsPOSTRequest("http://localhost:8080/user", gson.toJson(coordinate).getBytes());
    }
}
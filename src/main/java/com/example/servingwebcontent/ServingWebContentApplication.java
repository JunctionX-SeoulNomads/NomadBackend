package com.example.servingwebcontent;

import com.example.NomadUtils;
import com.example.data.Coordinate;
import com.example.data.NomadUser;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServingWebContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);

        NomadUser nomadUser = new NomadUser("Aziz", 1111.2222, 3333.4444);
        Coordinate coordinate = new Coordinate(23.46, 4544.4444);
        Gson gson = new Gson();
        System.out.println(NomadUtils.httpsPOSTRequest("http://localhost:8080/parking", gson.toJson(coordinate).getBytes()));
        System.out.println(NomadUtils.httpsPOSTRequest("http://localhost:8080/driver", gson.toJson(coordinate).getBytes()));
        System.out.println(NomadUtils.httpsPOSTRequest("http://localhost:8080/user", gson.toJson(nomadUser).getBytes()));
        System.out.println(gson.toJson(nomadUser));
    }
}
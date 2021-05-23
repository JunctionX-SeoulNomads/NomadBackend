package com.example.servingwebcontent;

import com.example.data.Coordinate;
import com.example.data.NomadUser;
import com.example.data.UserComplain;
import com.example.utils.NomadUtils;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServingWebContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);

        /*

        Coordinate coordinate = new Coordinate(23.46, 4544.4444);
        Gson gson = new Gson();
//        System.out.println("\nParking::::\n");
//        System.out.println(gson.toJson(coordinate));
//        System.out.println(NomadUtils.httpsPOSTRequest("http://localhost:8080/parking", gson.toJson(coordinate).getBytes()));

        System.out.println("\nDriver::::\n");
        System.out.println(gson.toJson(coordinate));
        System.out.println(NomadUtils.httpsPOSTRequest("http://localhost:8080/parking", gson.toJson(coordinate).getBytes()));

        while (true) {
//            System.out.println("\nDriver::::\n");
//            System.out.println(gson.toJson(coordinate));
            System.out.println(NomadUtils.httpsPOSTRequest("http://localhost:8080/driver", gson.toJson(coordinate).getBytes()));
        }
//        System.out.println("\nUser::::\n");
//        NomadUser nomadUser = new NomadUser("4235", 1111.2222, 3333.4444);
//        System.out.println(gson.toJson(nomadUser));
//        System.out.println(NomadUtils.httpsPOSTRequest("http://localhost:8080/user", gson.toJson(nomadUser).getBytes()));
//
//        System.out.println("\nComplain::::\n");
//        UserComplain complain = new UserComplain("1253", "bad parking", 100.10, 14.34);
//        System.out.println(gson.toJson(complain));
//        System.out.println(NomadUtils.httpsPOSTRequest("http://localhost:8080/complain", gson.toJson(complain).getBytes()));

         //*/
    }
}
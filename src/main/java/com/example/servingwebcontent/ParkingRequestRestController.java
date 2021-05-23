package com.example.servingwebcontent;


import com.example.data.Cluster;
import com.example.data.Coordinate;
import com.example.data.Status;
import com.example.servingwebcontent.components.Hierarchy;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class ParkingRequestRestController {

    private final Hierarchy hierarchy;

    @Autowired
    ParkingRequestRestController(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    @RequestMapping(value = "/parking", method = RequestMethod.POST)
    @ResponseBody
    public String processParkingCoordinates(@RequestBody Coordinate coordinate) {
        System.out.println("LOG get parking coordinates :\n" +
                "Longitude = " + coordinate.getLongitude() + "\n" +
                "Latitude = " + coordinate.getLatitude());

        int clusterMonthlyStatistics = 0;
        if (hierarchy.hasCluster()) {
            final Cluster nearestCluster = hierarchy.getNearestCluster(coordinate);
            clusterMonthlyStatistics = nearestCluster.getMonthlyStatistic();
        }

        if (clusterMonthlyStatistics > 30) {
            clusterMonthlyStatistics = 2;
        }
        else if (clusterMonthlyStatistics > 15) {
            clusterMonthlyStatistics = 1;
        }
        else {
            clusterMonthlyStatistics = 0;
        }

        Random random = new Random();
        clusterMonthlyStatistics = Math.abs(random.nextInt()) % 2;


        Status status = new Status(clusterMonthlyStatistics);
        Gson gson = new Gson();
        System.out.println("from parking : " + gson.toJson(status));
        return gson.toJson(status);
    }
}

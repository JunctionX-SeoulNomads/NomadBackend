package com.example.servingwebcontent;


import com.example.Main;
import com.example.data.Cluster;
import com.example.data.Coordinate;
import com.example.data.Status;
import com.example.servingwebcontent.components.Hierarchy;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class DriverRequestRestController {
    private final Hierarchy hierarchy;

    @Autowired
    DriverRequestRestController(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    @RequestMapping(value = "/driver", method = RequestMethod.POST)
    @ResponseBody
    public String processDriverCoordinates(@RequestBody Coordinate coordinate) {
        System.out.println("LOG get driver coordinates :\n" +
                "Longitude = " + coordinate.getLongitude() + "\n" +
                "Latitude = " + coordinate.getLatitude());

        int clusterStatus = 0;
        if (hierarchy.hasCluster()) {
            final Cluster nearestCluster = hierarchy.getNearestCluster(coordinate);
            clusterStatus = nearestCluster.getAliveCounter();
        }

        if (clusterStatus >= 4) {
            clusterStatus = 4;
        }
        else if (clusterStatus >= 2) {
            clusterStatus = 2;
        }
        else {
            clusterStatus = 0;
        }

        Random random = new Random();
        clusterStatus = Math.abs(random.nextInt()) % 3;

        Status status = new Status(clusterStatus);

        Gson gson = new Gson();
        System.out.println(gson.toJson(status));
        return gson.toJson(status);
    }

}

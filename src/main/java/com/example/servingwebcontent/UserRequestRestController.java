package com.example.servingwebcontent;

import com.example.data.Cluster;
import com.example.data.Coordinate;
import com.example.servingwebcontent.components.Hierarchy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRequestRestController {
    private final Hierarchy hierarchy;

    @Autowired
    UserRequestRestController(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public String processUserCoordinates(@RequestBody Coordinate coordinate) {
        if (hierarchy.hasCluster()) {
            final Cluster nearestCluster = hierarchy.getNearestCluster(coordinate);
            nearestCluster.incrementCounter();
        }

        System.out.println("LOG get user coordinates :\n" +
                "Longitude = " + coordinate.getLongitude() + "\n" +
                "Latitude = " + coordinate.getLatitude());

        return "OK";
    }
}

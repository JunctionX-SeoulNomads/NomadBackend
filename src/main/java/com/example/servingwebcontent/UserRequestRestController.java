package com.example.servingwebcontent;

import com.example.data.Cluster;
import com.example.data.Coordinate;
import com.example.data.NomadUser;
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
    public String processUserCoordinates(@RequestBody NomadUser nomadUser) {
        System.out.println("LOG get user :\n" +
                "userId = " + nomadUser.getUserId() + "\n" +
                "Longitude = " + nomadUser.getLongitude() + "\n" +
                "Latitude = " + nomadUser.getLatitude());

        if (hierarchy.hasCluster()) {
            final Cluster nearestCluster = hierarchy.getNearestCluster(nomadUser.getCoordinates());
            final int userLastClusterId = hierarchy.getClusterIdByUserId(nomadUser.getUserId());
            if (userLastClusterId == -1) {
                nearestCluster.incrementAliveCounter();
            }
            else if (nearestCluster.getClusterId() != userLastClusterId) {
                hierarchy.getClusterById(userLastClusterId).decrementAliveCounter();
                nearestCluster.incrementAliveCounter();
            }
        }

        return "OK";
    }
}

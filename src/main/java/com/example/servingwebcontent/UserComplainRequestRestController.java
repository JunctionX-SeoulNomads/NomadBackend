package com.example.servingwebcontent;

import com.example.data.Cluster;
import com.example.data.UserComplain;
import com.example.servingwebcontent.components.Hierarchy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserComplainRequestRestController {
    private final Hierarchy hierarchy;

    @Autowired
    UserComplainRequestRestController(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    @RequestMapping(value = "/complain", method = RequestMethod.POST)
    @ResponseBody
    public String processUserComplain(@RequestBody UserComplain userComplain) {
        System.out.println("LOG got complain :\n" +
                "userId = " + userComplain.getUserId() + "\n" +
                "Longitude = " + userComplain.getLongitude() + "\n" +
                "Latitude = " + userComplain.getLatitude() + "\n" +
                "message = " + userComplain.getMessage());

        if (hierarchy.hasCluster()) {
            final Cluster nearestCluster = hierarchy.getNearestCluster(userComplain.getCoordinates());
            hierarchy.saveComplain(userComplain, nearestCluster);
        }

        return "OK";
    }

}

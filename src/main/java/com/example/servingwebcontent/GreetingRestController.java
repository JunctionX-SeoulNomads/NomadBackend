package com.example.servingwebcontent;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingRestController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String greetingController() {
        System.out.println("LOG: got greeting request");
        return "Hello from Nomads team";
    }
}

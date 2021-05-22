package com.example.servingwebcontent;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class GreetingRestControllerTest {

    @Test
    public void responseIsNotEmpty() {
        final GreetingRestController greetingRestController = new GreetingRestController();
        final String response = greetingRestController.greetingController();
        assertNotEquals(null, response);
        assertNotEquals("", response);

    }
}

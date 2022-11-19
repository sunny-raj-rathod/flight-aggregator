package org.deblock.exercise.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightAggregationController {

    @RequestMapping("/")
    public String home() {
        return "Hello Deblock!";
    }

    @RequestMapping(value = "/v1/search")
    public ResponseEntity<Object> getFlights() {
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }
}

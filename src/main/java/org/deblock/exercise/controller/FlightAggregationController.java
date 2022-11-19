package org.deblock.exercise.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.deblock.exercise.entity.FlightSearchResponse;
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
    public ResponseEntity<ArrayList<FlightSearchResponse>> getFlights() {
        FlightSearchResponse searchResponse = new FlightSearchResponse();
        searchResponse.setAirline("Ryan Air");
        searchResponse.setSupplier("ToughJet");
        searchResponse.setFare(200.00);
        searchResponse.setDepartureAirportCode("LHR");
        searchResponse.setDestinationAirportCode("AMS");
        searchResponse.setDepartureDate(LocalDateTime.now());
        searchResponse.setArrivalDate(LocalDateTime.now());
        ArrayList<FlightSearchResponse> list = new ArrayList<>();
        list.add(searchResponse);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}

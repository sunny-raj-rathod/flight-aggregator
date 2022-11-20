package org.deblock.exercise.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightSearchRequestParameters {

    private String origin;

    private String destination;

    private int numberOfPassengers;

    private String departureDate;

    private String returnDate;
}
package org.deblock.exercise.integration.crazyair;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrazyAirSearchRequest {

    private String origin;

    private String destination;

    private int passengerCount;

    private String departureDate;

    private String returnDate;
}
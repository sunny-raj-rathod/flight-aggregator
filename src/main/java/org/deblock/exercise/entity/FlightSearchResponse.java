package org.deblock.exercise.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Search Response
 * airline Name of Airline
 * supplier Eg: CrazyAir or ToughJet
 * fare Total price rounded to 2 decimals
 * departureAirportCode 3 letter IATA code(eg. LHR, AMS)
 * destinationAirportCode 3 letter IATA code(eg. LHR, AMS)
 * departureDate ISO_DATE_TIME format
 * arrivalDate ISO_DATE_TIME format
 */
@Getter
@Setter
public class FlightSearchResponse {

    private String airline;

    private String supplier;

    private Double fare;

    private String departureAirportCode;

    private String destinationAirportCode;

    private LocalDateTime departureDate;

    private LocalDateTime arrivalDate;
}
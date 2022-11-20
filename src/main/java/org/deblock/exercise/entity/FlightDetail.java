package org.deblock.exercise.entity;

import org.deblock.exercise.integration.SupplierEnum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightDetail {

    private String airline;

    private SupplierEnum supplier;

    private Double fare;

    private String departureAirportCode;

    private String destinationAirportCode;

    private String departureDate;

    private String arrivalDate;
}
package org.deblock.exercise.integration.toughjet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ToughJetFlightDetail {

    private String carrier;

    private Double basePrice;

    private Double tax;

    private Double discount;

    private String departureAirportName;

    private String arrivalAirportName;

    private String outboundDateTime;

    private String inboundDateTime;
}
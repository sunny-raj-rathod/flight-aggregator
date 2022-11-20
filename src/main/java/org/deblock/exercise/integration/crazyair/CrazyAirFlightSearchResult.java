package org.deblock.exercise.integration.crazyair;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrazyAirFlightSearchResult {
    private List<CrazyAirFlightDetail> responses;
}
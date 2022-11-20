package org.deblock.exercise.integration.toughjet;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ToughJetFlightSearchResult {
    private List<ToughJetFlightDetail> responses;
}
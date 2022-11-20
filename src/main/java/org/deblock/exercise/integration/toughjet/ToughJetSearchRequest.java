package org.deblock.exercise.integration.toughjet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToughJetSearchRequest {

    private String from;

    private String to;

    private int numberOfAdults;

    private String outboundDate;

    private String inboundDate;
}

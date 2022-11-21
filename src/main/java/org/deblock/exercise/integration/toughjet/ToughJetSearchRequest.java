package org.deblock.exercise.integration.toughjet;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToughJetSearchRequest {

    private String from;

    private String to;

    private int numberOfAdults;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate outboundDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate inboundDate;
}

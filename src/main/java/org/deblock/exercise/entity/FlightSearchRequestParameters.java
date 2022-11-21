package org.deblock.exercise.entity;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class FlightSearchRequestParameters {

    @NotNull(message = "The origin is required.")
    @Size(min = 3, max = 3, message = "The length of origin must be 3 characters.")
    private String origin;

    @NotNull(message = "The destination is required.")
    @Size(min = 3, max = 3, message = "The length of destination must be 3 characters.")
    private String destination;

    @Min(value = 1, message = "The number of passengers must be greater than or equal to 1.")
    @Max(value = 4, message = "The number of passengers must be less than or equal to 4.")
    private int numberOfPassengers;

    @NotNull(message = "The departure date is required.")
    @FutureOrPresent(message = "The departure date must be today or in the future.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate departureDate;

    @FutureOrPresent(message = "The return date must be today or in the future.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate returnDate;
}
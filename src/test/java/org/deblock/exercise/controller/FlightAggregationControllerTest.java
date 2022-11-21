package org.deblock.exercise.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.deblock.exercise.entity.FlightDetail;
import org.deblock.exercise.entity.FlightSearchRequestParameters;
import org.deblock.exercise.entity.FlightSearchResult;
import org.deblock.exercise.integration.SupplierEnum;
import org.deblock.exercise.service.FlightAggregationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class FlightAggregationControllerTest {

    @MockBean
    FlightAggregationService mockService;

    @InjectMocks
    @Autowired
    FlightAggregationController flightAggregationController;

    @BeforeEach
    public void init() {
        List<FlightDetail> flightDetails = new ArrayList<>();
        FlightDetail flightDetail1 = new FlightDetail();
        flightDetail1.setAirline("Ryan Air");
        flightDetail1.setArrivalDate(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        flightDetail1.setDepartureDate(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        flightDetail1.setSupplier(SupplierEnum.CRAZYAIR);
        flightDetail1.setFare(200.00);
        flightDetail1.setDepartureAirportCode("LHR");
        flightDetail1.setDestinationAirportCode("AMS");
        FlightDetail flightDetail2 = new FlightDetail();
        flightDetail2.setAirline("Abc Air");
        flightDetail2.setArrivalDate(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        flightDetail2.setDepartureDate(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        flightDetail2.setSupplier(SupplierEnum.CRAZYAIR);
        flightDetail2.setFare(300.00);
        flightDetail2.setDepartureAirportCode("LHR");
        flightDetail2.setDestinationAirportCode("AMS");
        FlightDetail flightDetail3 = new FlightDetail();
        flightDetail3.setAirline("zyx Air");
        flightDetail3.setArrivalDate(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        flightDetail3.setDepartureDate(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        flightDetail3.setSupplier(SupplierEnum.TOUGHJET);
        flightDetail3.setFare(100.00);
        flightDetail3.setDepartureAirportCode("LHR");
        flightDetail3.setDestinationAirportCode("AMS");
        flightDetails.add(flightDetail1);
        flightDetails.add(flightDetail2);
        flightDetails.add(flightDetail3);
        when(mockService.getFlights(any(), any())).thenReturn(flightDetails);
    }

    @Test
    public void testGetFlights() throws Exception {
        FlightSearchRequestParameters searchParameters = new FlightSearchRequestParameters();
        searchParameters.setOrigin("AMS");
        searchParameters.setDestination("LHR");
        searchParameters.setNumberOfPassengers(1);
        searchParameters.setDepartureDate(LocalDate.now());
        ResponseEntity<FlightSearchResult> response = flightAggregationController.getFlights(searchParameters, 0, 10,
                "fare",
                "asc");
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getResponses()).hasSize(3);
    }

    @Test
    public void testNumberOfPassengersValidation() throws Exception {
        FlightSearchRequestParameters searchParameters = new FlightSearchRequestParameters();
        searchParameters.setOrigin("AMS");
        searchParameters.setDestination("LHR");
        searchParameters.setNumberOfPassengers(-1);
        searchParameters.setDepartureDate(LocalDate.now());
        assertThrows(ConstraintViolationException.class, () -> {
            flightAggregationController.getFlights(searchParameters, 0,
                    10,
                    "fare",
                    "asc");
        });
    }

    @Test
    public void testDepartureDateValidation() throws Exception {
        FlightSearchRequestParameters searchParameters = new FlightSearchRequestParameters();
        searchParameters.setOrigin("AMS");
        searchParameters.setDestination("LHR");
        searchParameters.setNumberOfPassengers(1);
        searchParameters.setDepartureDate(LocalDate.now().plusDays(-2));
        assertThrows(ConstraintViolationException.class, () -> {
            flightAggregationController.getFlights(searchParameters, 0,
                    10,
                    "fare",
                    "asc");
        });
    }

    @Test
    public void testReturnDateValidation() throws Exception {
        FlightSearchRequestParameters searchParameters = new FlightSearchRequestParameters();
        searchParameters.setOrigin("AMS");
        searchParameters.setDestination("LHR");
        searchParameters.setNumberOfPassengers(1);
        searchParameters.setDepartureDate(LocalDate.now());
        searchParameters.setReturnDate(LocalDate.now().plusDays(-2));
        assertThrows(ConstraintViolationException.class, () -> {
            flightAggregationController.getFlights(searchParameters, 0,
                    10,
                    "fare",
                    "asc");
        });
    }

    @Test
    public void testOriginValidation() throws Exception {
        FlightSearchRequestParameters searchParameters = new FlightSearchRequestParameters();
        searchParameters.setOrigin("AM");
        searchParameters.setDestination("LHR");
        searchParameters.setNumberOfPassengers(1);
        searchParameters.setDepartureDate(LocalDate.now());
        searchParameters.setReturnDate(LocalDate.now().plusDays(2));
        assertThrows(ConstraintViolationException.class, () -> {
            flightAggregationController.getFlights(searchParameters, 0,
                    10,
                    "fare",
                    "asc");
        });
    }

    @Test
    public void testDestinationValidation() throws Exception {
        FlightSearchRequestParameters searchParameters = new FlightSearchRequestParameters();
        searchParameters.setOrigin("AMS");
        searchParameters.setDestination("LH");
        searchParameters.setNumberOfPassengers(1);
        searchParameters.setDepartureDate(LocalDate.now());
        searchParameters.setReturnDate(LocalDate.now().plusDays(2));
        assertThrows(ConstraintViolationException.class, () -> {
            flightAggregationController.getFlights(searchParameters, 0,
                    10,
                    "fare",
                    "asc");
        });
    }
}

package org.deblock.exercise.service;

import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.deblock.exercise.entity.FlightDetail;
import org.deblock.exercise.integration.SupplierEnum;
import org.deblock.exercise.repository.IFlightSearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FlightAggregationServiceTests {

    @MockBean
    IFlightSearchRepository mockRepository;

    @InjectMocks
    @Autowired
    FlightAggregationService flightAggregationService;

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
        when(mockRepository.fetchFlights(null, null)).thenReturn(flightDetails);
    }

    @Test
    public void testFlightSearch() throws Exception {
        List<FlightDetail> flightDetails = flightAggregationService.getFlights(null, null);
        assertThat(flightDetails).isNotNull();
        assertThat(flightDetails).hasSize(3);
    }
}

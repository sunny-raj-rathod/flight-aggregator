package org.deblock.exercise.integration;

import java.util.List;
import java.util.stream.Collectors;

import org.deblock.exercise.entity.FlightSearchRequestParameters;
import org.deblock.exercise.entity.FlightDetail;
import org.springframework.web.client.RestTemplate;

public abstract class FlightSupplierIntegrationBase<T1, T2> implements IFlightSupplierIntegration<T1, T2> {

    protected RestTemplate restTemplate;

    public FlightSupplierIntegrationBase(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void findFlights(FlightSearchRequestParameters request, List<FlightDetail> flightDetails) {
        List<T2> responses = findFlightsImpl(buildSearchRequest(request));
        List<FlightDetail> flightDetailsList = responses.stream()
                .map(response -> transformResponse(response)).collect(Collectors.toList());
        flightDetails.addAll(flightDetailsList);
    }
}

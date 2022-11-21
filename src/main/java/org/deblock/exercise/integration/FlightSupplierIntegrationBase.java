package org.deblock.exercise.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.deblock.exercise.entity.FlightSearchRequestParameters;
import org.deblock.exercise.entity.FlightDetail;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class FlightSupplierIntegrationBase<T1, T2> implements
        IFlightSupplierIntegration<T1, T2> {

    protected RestTemplate restTemplate;

    protected FlightSupplierIntegrationBase(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<FlightDetail> findFlights(FlightSearchRequestParameters request) {
        List<T2> responses = new ArrayList<>();
        try {
            responses = findFlightsImpl(buildSearchRequest(request));
        } catch (Exception exception) {
            // in case of third party failure
            log.error(exception.getMessage(), exception);
        }
        return responses.stream()
                .map(response -> {
                    return transformResponse(response);
                }).collect(Collectors.toList());
    }
}

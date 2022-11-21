package org.deblock.exercise.service;

import java.util.List;

import org.deblock.exercise.entity.FlightDetail;
import org.deblock.exercise.entity.FlightSearchRequestParameters;
import org.deblock.exercise.repository.IFlightSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FlightAggregationService {

    @Autowired
    IFlightSearchRepository repository;

    public List<FlightDetail> getFlights(
            FlightSearchRequestParameters searchParameters,
            Pageable pageable) {

        return repository.fetchFlights(searchParameters, pageable);
    }
}

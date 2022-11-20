package org.deblock.exercise.repository;

import java.util.List;

import org.deblock.exercise.entity.FlightSearchRequestParameters;
import org.deblock.exercise.entity.FlightDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface IFlightSearchRepository {
    List<FlightDetail> fetchFlights(
            FlightSearchRequestParameters searchParameters,
            Pageable pageable);
}

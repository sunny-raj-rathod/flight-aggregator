package org.deblock.exercise.integration;

import java.util.List;

import org.deblock.exercise.entity.FlightSearchRequestParameters;
import org.deblock.exercise.entity.FlightDetail;

public interface IFlightSupplierIntegration<T1, T2> {

    T1 buildSearchRequest(FlightSearchRequestParameters request);

    List<T2> findFlightsImpl(T1 request);

    FlightDetail transformResponse(T2 response);
}

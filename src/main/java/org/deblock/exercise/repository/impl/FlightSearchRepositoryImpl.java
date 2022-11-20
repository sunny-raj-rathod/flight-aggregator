package org.deblock.exercise.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.deblock.exercise.entity.FlightSearchRequestParameters;
import org.deblock.exercise.entity.FlightDetail;
import org.deblock.exercise.integration.FlightSupplierIntegrationBase;
import org.deblock.exercise.integration.crazyair.CrazyAirFlightSearchClient;
import org.deblock.exercise.integration.toughjet.ToughJetFlightSearchClient;
import org.deblock.exercise.repository.IFlightSearchRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class FlightSearchRepositoryImpl implements IFlightSearchRepository {
    public List<FlightDetail> fetchFlights(
            FlightSearchRequestParameters searchParameters,
            Pageable pageable) {
        List<FlightDetail> flightDetails = new ArrayList<>();
        List<FlightSupplierIntegrationBase> suppliers = new ArrayList<FlightSupplierIntegrationBase>();
        suppliers.add(new CrazyAirFlightSearchClient());
        suppliers.add(new ToughJetFlightSearchClient());
        // Issue with parallel stream
        suppliers.forEach(supplier -> supplier.findFlights(searchParameters, flightDetails));
        // TODO: how to page and sort?
        return flightDetails;
    }
}

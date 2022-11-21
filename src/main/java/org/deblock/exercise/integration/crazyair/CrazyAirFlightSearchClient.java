package org.deblock.exercise.integration.crazyair;

import java.net.URI;
import java.util.List;

import org.deblock.exercise.entity.FlightSearchRequestParameters;
import org.deblock.exercise.entity.FlightDetail;
import org.deblock.exercise.integration.FlightSupplierIntegrationBase;
import org.deblock.exercise.integration.SupplierEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CrazyAirFlightSearchClient
        extends FlightSupplierIntegrationBase<CrazyAirSearchRequest, CrazyAirFlightDetail> {

    @Value("${crazyair.url}")
    private String crazyAirUrl;

    @Autowired
    public CrazyAirFlightSearchClient() {
        super(new RestTemplate());
    }

    public CrazyAirSearchRequest buildSearchRequest(FlightSearchRequestParameters request) {
        CrazyAirSearchRequest crazyAirSearchRequest = new CrazyAirSearchRequest();
        crazyAirSearchRequest.setDepartureDate(request.getDepartureDate());
        crazyAirSearchRequest.setDestination(request.getDestination());
        crazyAirSearchRequest.setOrigin(request.getOrigin());
        crazyAirSearchRequest.setPassengerCount(request.getNumberOfPassengers());
        crazyAirSearchRequest.setReturnDate(request.getReturnDate());
        return crazyAirSearchRequest;
    }

    public List<CrazyAirFlightDetail> findFlightsImpl(CrazyAirSearchRequest request) {
        // TODO:URI to be moved to application.properties, unable to view property in
        // tests
        RequestEntity<CrazyAirSearchRequest> requestEntity = new RequestEntity<>(
                request, HttpMethod.POST,
                URI.create("http://localhost:8001/v1/search"));
        ResponseEntity<CrazyAirFlightSearchResult> result = this.restTemplate.exchange(
                requestEntity,
                CrazyAirFlightSearchResult.class);
        return result.getBody().getResponses();
    }

    public FlightDetail transformResponse(CrazyAirFlightDetail response) {
        FlightDetail flightSearchResponse = new FlightDetail();
        flightSearchResponse.setAirline(response.getAirline());
        flightSearchResponse.setSupplier(SupplierEnum.CRAZYAIR);
        flightSearchResponse.setFare(response.getPrice());
        flightSearchResponse.setDepartureAirportCode(response.getDepartureAirportCode());
        flightSearchResponse.setDestinationAirportCode(response.getDestinationAirportCode());
        flightSearchResponse.setDepartureDate(response.getDepartureDate());
        flightSearchResponse.setArrivalDate(response.getArrivalDate());
        return flightSearchResponse;
    }
}

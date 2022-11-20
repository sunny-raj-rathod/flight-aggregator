package org.deblock.exercise.integration.toughjet;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
public class ToughJetFlightSearchClient
        extends FlightSupplierIntegrationBase<ToughJetSearchRequest, ToughJetFlightDetail> {

    @Value("${toughjet.url}")
    private String toughJetUrl;

    @Autowired
    public ToughJetFlightSearchClient() {
        super(new RestTemplate());
    }

    public ToughJetSearchRequest buildSearchRequest(FlightSearchRequestParameters request) {
        ToughJetSearchRequest toughJetSearchRequest = new ToughJetSearchRequest();
        toughJetSearchRequest.setFrom(request.getOrigin());
        toughJetSearchRequest.setTo(request.getDestination());
        toughJetSearchRequest.setInboundDate(request.getDepartureDate());
        toughJetSearchRequest.setNumberOfAdults(request.getNumberOfPassengers());
        toughJetSearchRequest.setOutboundDate(request.getReturnDate());
        return toughJetSearchRequest;
    }

    public List<ToughJetFlightDetail> findFlightsImpl(ToughJetSearchRequest request) {
        RequestEntity<ToughJetSearchRequest> requestEntity = new RequestEntity<>(
                request, HttpMethod.POST,
                URI.create("http://localhost:8002/v1/search"));
        ResponseEntity<ToughJetFlightSearchResult> result = this.restTemplate.exchange(
                requestEntity,
                ToughJetFlightSearchResult.class);
        // TODO: null handling.
        return result.getBody().getResponses();
    }

    public FlightDetail transformResponse(ToughJetFlightDetail response) {
        Double fare = response.getBasePrice() + response.getTax();
        fare = fare - ((fare * response.getDiscount()) / 100);
        FlightDetail flightDetail = new FlightDetail();
        flightDetail.setAirline(response.getCarrier());
        flightDetail.setSupplier(SupplierEnum.TOUGHJET);// enum
        flightDetail.setFare(fare);
        flightDetail.setDepartureAirportCode(response.getDepartureAirportName());
        flightDetail.setDestinationAirportCode(response.getArrivalAirportName());
        flightDetail.setDepartureDate(response.getOutboundDateTime());
        flightDetail.setArrivalDate(response.getInboundDateTime());
        return flightDetail;
    }
}

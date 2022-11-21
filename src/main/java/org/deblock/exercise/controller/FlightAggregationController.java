package org.deblock.exercise.controller;

import java.util.List;

import javax.validation.Valid;

import org.deblock.exercise.entity.FlightSearchRequestParameters;
import org.deblock.exercise.entity.FlightSearchResult;
import org.deblock.exercise.entity.FlightDetail;
import org.deblock.exercise.service.FlightAggregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class FlightAggregationController {

    @Autowired
    FlightAggregationService flightAggregationService;

    @RequestMapping("/")
    public String home() {
        return "Hello Deblock!";
    }

    @PostMapping(value = "/v1/search")
    public ResponseEntity<FlightSearchResult> getFlights(
            @Valid @RequestBody FlightSearchRequestParameters searchParameters,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "fare") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        List<FlightDetail> flightDetails = flightAggregationService.getFlights(searchParameters, pageable);
        FlightSearchResult flightSearchResult = new FlightSearchResult();
        flightSearchResult.setResponses(flightDetails);

        return new ResponseEntity<>(flightSearchResult, HttpStatus.OK);
    }
}

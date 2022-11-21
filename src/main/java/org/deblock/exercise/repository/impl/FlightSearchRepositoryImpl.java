package org.deblock.exercise.repository.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.deblock.exercise.entity.FlightSearchRequestParameters;
import org.deblock.exercise.entity.FlightDetail;
import org.deblock.exercise.integration.FlightSupplierIntegrationBase;
import org.deblock.exercise.integration.SupplierEnum;
import org.deblock.exercise.integration.SupplierIntegrationFactory;
import org.deblock.exercise.repository.IFlightSearchRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Repository
public class FlightSearchRepositoryImpl implements IFlightSearchRepository {
    public List<FlightDetail> fetchFlights(
            FlightSearchRequestParameters searchParameters,
            Pageable pageable) {

        List<CompletableFuture<List<FlightDetail>>> futures = Stream.of(SupplierEnum.values())
                .map(supplier -> CompletableFuture.supplyAsync(() -> {
                    FlightSupplierIntegrationBase supplierInstance = SupplierIntegrationFactory
                            .getInstance(supplier);
                    List<FlightDetail> flights = supplierInstance.findFlights(searchParameters);
                    return flights;
                })).collect(Collectors.toList());

        List<FlightDetail> flightDetails = futures.stream()
                .reduce(combineApiCalls())
                .orElse(CompletableFuture.completedFuture(emptyList())).join();
        List<FlightDetail> list = new ArrayList<>(flightDetails);
        list.sort(Comparator.comparingDouble(FlightDetail::getFare));
        // TODO: explore paging and sorting
        return list;
    }

    private BinaryOperator<CompletableFuture<List<FlightDetail>>> combineApiCalls() {
        return (c1, c2) -> c1
                .thenCombine(c2, (list1, list2) -> {
                    return Stream.concat(list1.stream(), list2.stream()).collect(toList());
                });
    }
}

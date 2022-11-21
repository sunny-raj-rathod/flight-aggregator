package org.deblock.exercise.integration;

import java.util.Map;

import org.deblock.exercise.integration.crazyair.CrazyAirFlightSearchClient;
import org.deblock.exercise.integration.toughjet.ToughJetFlightSearchClient;

public class SupplierIntegrationFactory {
    private static final Map<SupplierEnum, FlightSupplierIntegrationBase> FACTORY = Map.of(
            SupplierEnum.CRAZYAIR, new CrazyAirFlightSearchClient(),
            SupplierEnum.TOUGHJET, new ToughJetFlightSearchClient());

    public static FlightSupplierIntegrationBase getInstance(SupplierEnum supplier) {
        return FACTORY.get(supplier);
    }
}

package org.deblock.exercise.controller;

import org.deblock.exercise.entity.FlightSearchRequestParameters;
import org.deblock.exercise.entity.FlightSearchResult;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FlightAggregationControllerTests {

        @LocalServerPort
        private int port;

        @Autowired
        private TestRestTemplate restTemplate;

        WireMockServer wireMockServerCrazyAir;

        WireMockServer wireMockServerToughJet;

        public void setupWireMock() throws Exception {
                wireMockServerCrazyAir = new WireMockServer(options().port(8001));
                wireMockServerCrazyAir.start();
                wireMockServerCrazyAir.stubFor(WireMock.post("/v1/search")
                                .willReturn(WireMock.aResponse().withStatus(200)
                                                .withHeader("Content-Type", "application/json")
                                                .withBodyFile("crazyair.json")));
                wireMockServerToughJet = new WireMockServer(options().port(8002));
                wireMockServerToughJet.start();
                wireMockServerToughJet.stubFor(WireMock.post("/v1/search")
                                .willReturn(WireMock.aResponse().withStatus(200)
                                                .withHeader("Content-Type", "application/json")
                                                .withBodyFile("toughjet.json")));
        }

        public void tearDownWireMock() throws Exception {
                wireMockServerCrazyAir.shutdownServer();
                wireMockServerToughJet.shutdownServer();
        }

        @Test
        public void defaultShouldReturnDefaultMessage() throws Exception {
                assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                                String.class)).contains("Hello Deblock!");
        }

        @Test
        public void searchShouldReturnOkAndResult() throws Exception {
                this.setupWireMock();
                String departureDate = DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now());
                FlightSearchRequestParameters searchParameters = new FlightSearchRequestParameters();
                searchParameters.setOrigin("AMS");
                searchParameters.setDestination("LHR");
                searchParameters.setNumberOfPassengers(1);
                searchParameters.setDepartureDate(departureDate);
                RequestEntity<FlightSearchRequestParameters> requestEntity = new RequestEntity<FlightSearchRequestParameters>(
                                searchParameters, HttpMethod.POST,
                                URI.create("http://localhost:" + port + "/v1/search"));
                ResponseEntity<FlightSearchResult> response = this.restTemplate.exchange(
                                requestEntity,
                                FlightSearchResult.class);
                assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
                assertThat(response.getBody().getResponses()).hasSize(6);
                this.tearDownWireMock();
        }
}

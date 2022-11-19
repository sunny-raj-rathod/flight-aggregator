package org.deblock.exercise.controller;

import org.deblock.exercise.entity.FlightSearchResponse;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FlightAggregationControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void defaultShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Hello Deblock!");
    }

    @Test
    public void searchShouldReturnOk() throws Exception {
        ResponseEntity<ArrayList> response = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/v1/search",
                ArrayList.class);
        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }
}

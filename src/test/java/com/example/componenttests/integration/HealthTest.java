package com.example.componenttests.integration;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;

import static com.example.componenttests.utils.AssertionsHelper.assertExpectedResponse;

@IntegrationTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HealthTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ApplicationContext applicationContext;

    @LocalServerPort
    int serverPort;

    @Test
    @Order(1)
    void livenessProbeFailure() {
        AvailabilityChangeEvent.publish(applicationContext, LivenessState.BROKEN);

        var response = restTemplate.getForEntity("http://localhost:{port}/livez", String.class, serverPort);

        assertExpectedResponse(response, HttpStatus.SERVICE_UNAVAILABLE, "{\"status\":\"DOWN\"}");
    }

    @Test
    @Order(2)
    void readinessProbeFailure() {
        AvailabilityChangeEvent.publish(applicationContext, ReadinessState.REFUSING_TRAFFIC);

        var response = restTemplate.getForEntity("http://localhost:{port}/readyz", String.class, serverPort);

        assertExpectedResponse(response, HttpStatus.SERVICE_UNAVAILABLE, "{\"status\":\"OUT_OF_SERVICE\"}");
    }
}

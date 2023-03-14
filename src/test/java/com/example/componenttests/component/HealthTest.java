package com.example.componenttests.component;

import com.example.componenttests.component.ComponentTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static com.example.componenttests.utils.AssertionsHelper.assertExpectedResponse;

@ComponentTest
class HealthTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Value("${embedded.service.port}")
    int serverPort;

    @ParameterizedTest
    @ValueSource(strings = {"livez", "readyz"})
    void exposure(String group) {
        var response = restTemplate.getForEntity("http://localhost:{port}/{group}", String.class, serverPort, group);

        assertExpectedResponse(response, HttpStatus.OK, "{\"status\":\"UP\"}");
    }
}

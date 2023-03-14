package com.example.componenttests.component;

import com.example.componenttests.component.ComponentTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentTest
class MetricsTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Value("${embedded.service.management.port}")
    int managementPort;

    @Test
    void jvmMemoryExposure() {
        var histogram = metric("jvm_memory_used_bytes")
                .and(tag("area", "heap"));

        assertThat(metrics().lines())
                .anyMatch(histogram);
    }

    String metrics() {
        return restTemplate.getForObject("http://localhost:{port}/metrics", String.class, managementPort);
    }

    Predicate<String> metric(String metricName) {
        return metric -> metric.contains(metricName);
    }

    Predicate<String> tag(String name, String value) {
        return metric -> metric.contains("%s=\"%s".formatted(name, value));
    }
}

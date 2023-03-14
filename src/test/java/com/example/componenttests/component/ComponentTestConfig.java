package com.example.componenttests.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        name = "embedded.service.enabled",
        havingValue = "true",
        matchIfMissing = true)
class ComponentTestConfig {

    @Value("${embedded.service.host}")
    String host;

    @Value("${embedded.service.port}")
    String port;

    @Bean
    TestRestTemplate testRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        var restTemplate = new TestRestTemplate(restTemplateBuilder);

        var handler = new RootUriTemplateHandler(String.format("http://%s:%s", host, port));
        restTemplate.setUriTemplateHandler(handler);

        return restTemplate;
    }

    @Bean
    RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }
}

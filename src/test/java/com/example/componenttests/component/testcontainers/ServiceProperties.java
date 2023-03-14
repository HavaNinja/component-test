package com.example.componenttests.component.testcontainers;

import com.playtika.test.common.properties.CommonContainerProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("embedded.service")
@Getter
@Setter
public class ServiceProperties extends CommonContainerProperties {

    private String dockerImage;
    private String defaultDockerImage;
    private Integer port = 8080;
    private Integer managementPort = 8081;
    private String healthPath = "/health";
}

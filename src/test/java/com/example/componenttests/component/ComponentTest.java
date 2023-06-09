package com.example.componenttests.component;


import com.example.componenttests.component.testcontainers.EmbeddedServiceBootstrapConfiguration;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Tag("component")
@SpringBootTest(webEnvironment = NONE, args = "build.image=true")
@TestPropertySource("classpath:/component-tests.properties")
@ContextConfiguration(classes = {ComponentTestConfig.class, EmbeddedServiceBootstrapConfiguration.class})
public @interface ComponentTest {
}

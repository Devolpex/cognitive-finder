package org.cognitivefinder.tracking.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AppConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testRestTemplateBean() {
        // Retrieve the RestTemplate bean from the application context
        RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);

        // Assert that the RestTemplate bean is not null, meaning it was created successfully
        assertNotNull(restTemplate, "RestTemplate bean should be created successfully");
    }
}

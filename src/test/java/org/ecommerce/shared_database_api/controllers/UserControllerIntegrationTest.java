package org.ecommerce.shared_database_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecommerce.shared_database_api.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations = "classpath:application-test.properties")
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void testCreateNewUser() throws Exception {
        // Prepare test data
        User user = new User();
        user.setFullName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("securepassword");
        user.setPhoneNumber("1234567890");
        user.setStatus("active");

        // Convert the user object to JSON
        String userJson = objectMapper.writeValueAsString(user);

        // Set up HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Set up HTTP entity
        HttpEntity<String> requestEntity = new HttpEntity<>(userJson, headers);

        // Perform the POST request
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/create_new_user",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Additional assertions can be made here based on the actual behavior of the endpoint
    }
}

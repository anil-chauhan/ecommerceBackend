package org.ecommerce.shared_database_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecommerce.shared_database_api.models.User;
import org.ecommerce.shared_database_api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("All tests in this class are disabled")
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCreateNewUser() throws Exception {
        // Create a User object for testing
        User user = new User();
        user.setFullName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("securepassword");
        user.setPhoneNumber("1234567890");
        user.setStatus("active");

        // Define behavior for the mocked UserService
        doNothing().when(userService).createUser(user);

        // Perform the POST request and validate the response
        mockMvc.perform(post("/create_new_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk());

        // Verify that the UserService's createUser method was called once
        verify(userService).createUser(user);
    }
}

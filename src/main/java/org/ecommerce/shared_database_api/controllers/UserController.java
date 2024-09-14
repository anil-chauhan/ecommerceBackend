package org.ecommerce.shared_database_api.controllers;


import org.ecommerce.shared_database_api.models.User;
import org.ecommerce.shared_database_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create_new_user")
    public void createNewUser(@RequestBody User newUser) {
        userService.createUser(newUser);
    }

}

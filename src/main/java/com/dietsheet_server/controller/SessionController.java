package com.dietsheet_server.controller;

import com.dietsheet_server.model.User;
import com.dietsheet_server.security.UserAuthenticationService;
import com.dietsheet_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Transactional
public class SessionController {

    @Autowired
    UserAuthenticationService userAuthenticationService;

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public String createUser(@RequestBody final User user) {
        userService.save(user);
        return createSession(user);
    }

    @PostMapping("/session")
    public String createSession(@RequestBody final User user) {
        return userAuthenticationService.login(user.getUsername(), user.getPassword());
    }

}

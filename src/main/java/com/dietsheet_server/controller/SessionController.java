package com.dietsheet_server.controller;

import com.dietsheet_server.DAO.UserDAO;
import com.dietsheet_server.model.User;
import com.dietsheet_server.security.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@Transactional
public class SessionController {

    @Autowired
    UserAuthenticationService userAuthenticationService;

    @Autowired
    UserDAO userDAO;

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody final User user) {
        userDAO.save(user);
        return createSession(user);
    }

    @PostMapping("/session")
    public ResponseEntity<User> createSession(@RequestBody final User user) {
        User updatedUser = userAuthenticationService.login(user.getUsername(), user.getPassword());
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}

package com.dietsheet_server.controller;

import com.dietsheet_server.DAO.UserDAO;
import com.dietsheet_server.model.User;
import com.dietsheet_server.security.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/users")
@Transactional
public class UserController {

    @Autowired
    UserAuthenticationService userAuthenticationService;

    @Autowired
    UserDAO userDAO;

    @PostMapping("/register")
    public String register(@RequestParam("username") final String username,
                    @RequestParam("password") final String password) {

        User newUser = new User();

        newUser.setUsername(username);
        newUser.setPassword(password);

        userDAO.save(newUser);

        return login(username, password);
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        return userAuthenticationService.login(username, password);
    }
}

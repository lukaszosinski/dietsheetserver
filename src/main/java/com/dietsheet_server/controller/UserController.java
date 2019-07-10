package com.dietsheet_server.controller;

import com.dietsheet_server.DAO.UserDAO;
import com.dietsheet_server.model.User;
import com.dietsheet_server.security.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.removeStart;

@RestController
@RequestMapping("/public/user")
@Transactional
public class UserController {

    @Autowired
    UserAuthenticationService userAuthenticationService;

    @Autowired
    UserDAO userDAO;

    @PostMapping("/register")
    public String register(@RequestHeader("authorization") final String authorization) {
        final String basicAuth = removeStart(authorization, "Basic").trim();

        Map<String, String> userData = getUserDataFromAuthHeader(basicAuth);

        User newUser = new User();

        newUser.setUsername(userData.get("username"));
        newUser.setPassword(userData.get("password"));

        userDAO.save(newUser);

        return userAuthenticationService.login(newUser.getUsername(), newUser.getPassword());
    }

    @PostMapping("/login")
    public String login(@RequestHeader("authorization") final String authorization) {
        final String basicAuth = removeStart(authorization, "Basic").trim();

        Map<String, String> userData = getUserDataFromAuthHeader(basicAuth);

        return userAuthenticationService.login(userData.get("username"), userData.get("password"));
    }

    private Map<String, String> getUserDataFromAuthHeader(String auth) {
        Map<String, String> userData = new HashMap<>();

        String[] data = new String(Base64
                .getDecoder()
                .decode(auth)
        ).split(":");

        userData.put("username", data[0]);
        userData.put("password", data[1]);

        return userData;
    }
}

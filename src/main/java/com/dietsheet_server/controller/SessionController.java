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
@RequestMapping("/public")
@Transactional
public class SessionController {

    @Autowired
    UserAuthenticationService userAuthenticationService;

    @Autowired
    UserDAO userDAO;

    @PostMapping("/user")
    public String createUser(@RequestHeader("authorization") final String authorization) {
        final String authHeader = removeStart(authorization, "Basic").trim();

        Map<String, String> userData = getUserDataFromAuthHeader(authHeader);

        User newUser = new User();

        newUser.setUsername(userData.get("username"));
        newUser.setPassword(userData.get("password"));

        userDAO.save(newUser);

        return userAuthenticationService.login(newUser.getUsername(), newUser.getPassword());
    }

    @PostMapping("/session")
    public String createSession(@RequestHeader("authorization") final String authorization) {
        final String authHeader = removeStart(authorization, "Basic").trim();

        Map<String, String> userData = getUserDataFromAuthHeader(authHeader);

        return userAuthenticationService.login(userData.get("username"), userData.get("password"));
    }

    private Map<String, String> getUserDataFromAuthHeader(String auth) {
        Map<String, String> userData = new HashMap<>();
        String[] data = null;

        try {
             data = new String(Base64
                    .getDecoder()
                    .decode(auth)
            ).split(":");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RuntimeException("invalid Basic authorization header");
        }


        userData.put("username", data[0]);
        userData.put("password", data[1]);

        return userData;
    }
}

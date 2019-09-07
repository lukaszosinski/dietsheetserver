package com.dietsheet_server.controller;

import com.dietsheet_server.model.user.User;
import com.dietsheet_server.security.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
@Transactional
public class SecuredSessionController {

    @Autowired
    UserAuthenticationService authentication;

    @DeleteMapping("")
    boolean deleteSession(@AuthenticationPrincipal final User user) {
        authentication.logout(user);
        return true;
    }
}

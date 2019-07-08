package com.dietsheet_server.controller;

import com.dietsheet_server.model.User;
import com.dietsheet_server.security.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Transactional
public class SecuredUserController {

    @Autowired
    UserAuthenticationService authentication;

    @GetMapping("/logout")
    boolean logout(@AuthenticationPrincipal final User user) {
        authentication.logout(user);
        return true;
    }
}

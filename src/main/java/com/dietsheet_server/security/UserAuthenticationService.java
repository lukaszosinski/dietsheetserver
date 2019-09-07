package com.dietsheet_server.security;

import com.dietsheet_server.model.user.User;

public interface UserAuthenticationService {

    User login(String username, String password);

    User findByToken(String token);

    void logout(User user);
}

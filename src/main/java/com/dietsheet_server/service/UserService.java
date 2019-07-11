package com.dietsheet_server.service;

import com.dietsheet_server.model.User;

public interface UserService {
    void save(User user);

    User find(long id);

    User findByUsername(String username);
}

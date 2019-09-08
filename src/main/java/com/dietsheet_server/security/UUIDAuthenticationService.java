package com.dietsheet_server.security;

import com.dietsheet_server.DAO.UserDAO;
import com.dietsheet_server.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UUIDAuthenticationService implements UserAuthenticationService {

    @Autowired
    UserDAO userDAO;

    @Override
    public User login(final String username, final String password) {
        final String uuid = UUID.randomUUID().toString();

        final User user = userDAO.getByName(username);

        if (user != null && user.getPassword().equals(password)) {
            user.setToken(uuid);
            userDAO.update(user);
            return user;
        }

        throw new RuntimeException("invalid login and/or password");
    }

    @Override
    public User findByToken(final String token) {
        return userDAO.getByToken(token);
    }

    @Override
    public void logout(final User user) {
        user.setToken("");
        userDAO.update(user);
    }
}

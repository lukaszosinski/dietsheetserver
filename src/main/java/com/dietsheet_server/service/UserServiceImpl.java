package com.dietsheet_server.service;

import com.dietsheet_server.DAO.UserDAO;
import com.dietsheet_server.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public void save(User user) {
        userDAO.update(user);
    }

    @Override
    public User find(long id) {
        return userDAO.get(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.getByName(username);
    }
}

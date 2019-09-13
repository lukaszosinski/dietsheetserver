package com.dietsheet_server.service;

import com.dietsheet_server.model.user.User;
import com.dietsheet_server.model.user.UserData;
import com.dietsheet_server.model.user.UserDataSnapshot;
import com.dietsheet_server.model.user.UserPreferences;
import com.dietsheet_server.model.user.dietlimits.DietLimits;

import java.util.List;

public interface UserService {
    void save(User user);
    User find(long id);
    User findByUsername(String username);

    DietLimits getUserDietLimits(User user);
    UserData getUserData(User user);
    List<UserDataSnapshot> getUserDataHistory(User user);
    UserPreferences getUserPreferences(User user);

    void updateUserDietLimits(User user, DietLimits dietLimits);
    void updateUserData(User user, UserData userData);
    void updateUserPreferences(User user, UserPreferences userPreferences);
}

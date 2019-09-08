package com.dietsheet_server.service;

import com.dietsheet_server.DAO.UserDAO;
import com.dietsheet_server.model.user.User;
import com.dietsheet_server.model.user.UserData;
import com.dietsheet_server.model.user.UserPreferences;
import com.dietsheet_server.model.user.dietlimits.DietLimits;
import com.dietsheet_server.model.user.dietlimits.DietLimitsCalculationStrategyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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

    @Override
    public DietLimits getUserDietLimits(User user) {
        DietLimits dietLimits = userDAO.get(user.getId()).getDietLimits();
        if(dietLimits == null) {
            throw new ResourceNotFoundException();
        }
        return dietLimits;
    }

    @Override
    public UserData getUserData(User user) {
        UserData userData = userDAO.get(user.getId()).getData();
        if(userData == null) {
            throw new ResourceNotFoundException();
        }
        return userData;
    }

    @Override
    public UserPreferences getUserPreferences(User user) {
        UserPreferences userPreferences = userDAO.get(user.getId()).getPreferences();
        if(userPreferences == null) {
            throw new ResourceNotFoundException();
        }
        return userPreferences;
    }

    @Override
    public void updateUserDietLimits(User user, DietLimits dietLimits) {
        User userToUpdate = userDAO.get(user.getId());
        userToUpdate.setDietLimits(dietLimits);
        userToUpdate.getPreferences().setStrategyEnum(DietLimitsCalculationStrategyEnum.MANUAL);
        userDAO.update(userToUpdate);
    }

    @Override
    public void updateUserData(User user, UserData userData) {
        User userToUpdate = userDAO.get(user.getId());
        UserData userDataToUpdate = userToUpdate.getData();
        userDataToUpdate.setAge(userData.getAge());
        userDataToUpdate.setHeight(userData.getHeight());
        userDataToUpdate.setWeight(userData.getWeight());
        userDataToUpdate.setSex(userData.getSex());
        userDataToUpdate.setPhysicalActivity(userData.getPhysicalActivity());
        userDataToUpdate.calculateBMI();

        userToUpdate.setData(userDataToUpdate);
        userToUpdate.calculateDietLimits();

        userDAO.update(userToUpdate);
    }

    @Override
    public void updateUserPreferences(User user, UserPreferences userPreferences) {
        User userToUpdate = userDAO.get(user.getId());
        userToUpdate.setPreferences(userPreferences);
        userDAO.update(userToUpdate);
    }
}

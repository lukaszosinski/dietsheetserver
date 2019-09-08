package com.dietsheet_server.controller;

import com.dietsheet_server.model.user.User;
import com.dietsheet_server.model.user.UserData;
import com.dietsheet_server.model.user.UserPreferences;
import com.dietsheet_server.model.user.dietlimits.DietLimits;
import com.dietsheet_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal final User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user/dietLimits")
    public ResponseEntity<DietLimits> getUserDietLimits(@AuthenticationPrincipal final User user) {
        DietLimits userDietLimits = userService.getUserDietLimits(user);
        return new ResponseEntity<>(userDietLimits, HttpStatus.OK);
    }

    @PutMapping("/user/dietLimits")
    public ResponseEntity<DietLimits> updateUserDietLimits(
            @AuthenticationPrincipal final User user,
            @RequestBody DietLimits dietLimits) {
        userService.updateUserDietLimits(user, dietLimits);
        DietLimits updatedDietLimits = userService.getUserDietLimits(user);
        return new ResponseEntity<>(updatedDietLimits, HttpStatus.OK);
    }

    @PutMapping("/user/data")
    public ResponseEntity<UserData> updateUserData(
            @AuthenticationPrincipal final User user,
            @RequestBody UserData userData) {
        userService.updateUserData(user, userData);
        UserData updatedUserData = userService.getUserData(user);
        return new ResponseEntity<>(updatedUserData, HttpStatus.OK);
    }

    @GetMapping("/user/data")
    public ResponseEntity<UserData> getUserData(
            @AuthenticationPrincipal final User user) {
        UserData userData = userService.getUserData(user);
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @PutMapping("/user/preferences")
    public ResponseEntity<UserPreferences> updateUserPreferences(
            @AuthenticationPrincipal final User user,
            @RequestBody UserPreferences userPreferences) {
        userService.updateUserPreferences(user, userPreferences);
        UserPreferences updatedUserPreferences = userService.getUserPreferences(user);
        return new ResponseEntity<>(updatedUserPreferences, HttpStatus.OK);
    }

    @GetMapping("/user/preferences")
    public ResponseEntity<UserPreferences> getUserPreferences(
            @AuthenticationPrincipal final User user) {
        UserPreferences userPreferences = userService.getUserPreferences(user);
        return new ResponseEntity<>(userPreferences, HttpStatus.OK);
    }
}

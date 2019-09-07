package com.dietsheet_server.controller;

import com.dietsheet_server.model.diet.Meal;
import com.dietsheet_server.model.user.User;
import com.dietsheet_server.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MealController {

    @Autowired
    Service<Meal> mealService;

    @GetMapping(value = "/meal")
    public ResponseEntity<List<Meal>> getAllMeals(
            @RequestParam Map<String,String> params,
            @AuthenticationPrincipal User user) {
        List<Meal> meals = mealService.findAll(user, params);
        return new ResponseEntity<>(meals, HttpStatus.OK);
    }

    @GetMapping(value = "/meal/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> getMeal(@PathVariable("id") long id) {
        Meal meal = mealService.findById(id);
        return new ResponseEntity<>(meal, HttpStatus.OK);
    }

    @PostMapping(value = "/meal")
    public ResponseEntity<Meal> createMeal(
            @RequestBody Meal meal,
            @AuthenticationPrincipal User user) {
        mealService.save(meal, user);
        return new ResponseEntity<>(meal, HttpStatus.CREATED);
    }

    @PutMapping(value = "/meal/{id}")
    public ResponseEntity<Meal> updateMeal(
            @PathVariable("id") long id,
            @RequestBody Meal mealUpdateData) {
        mealService.update(mealUpdateData, id);
        Meal updatedMeal = mealService.findById(id);
        return new ResponseEntity<>(updatedMeal, HttpStatus.OK);
    }

    @DeleteMapping(value = "/meal/{id}")
    public ResponseEntity<Meal> deleteMeal(@PathVariable("id") long id) {
        mealService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/meal")
    public ResponseEntity<Meal> deleteAllMeals() {
        mealService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package com.dietsheet_server.controller;

import com.dietsheet_server.model.Meal;
import com.dietsheet_server.model.User;
import com.dietsheet_server.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MealController {

    @Autowired
    Service<Meal> mealService;

    @GetMapping(value = "/meal")
    public ResponseEntity<List<Meal>> getAllMeals(@AuthenticationPrincipal User user) {
        List<Meal> meals = mealService.findAll(user);
        if(meals.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
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

        if (mealService.isExist(meal)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        meal.setOwner(user);
        mealService.save(meal);
        meal = mealService.findById(meal.getId());
        meal.recalculateSummary();
        mealService.update(meal);

        return new ResponseEntity<>(meal, HttpStatus.CREATED);
    }

    @PutMapping(value = "/meal/{id}")
    public ResponseEntity<Meal> updateMeal(@PathVariable("id") long id, @RequestBody Meal meal) {
        Meal mealToUpdate = mealService.findById(id);

        mealToUpdate.setName(meal.getName());
        mealToUpdate.updateIngredients(meal.getIngredients());
        mealService.update(mealToUpdate);
        mealToUpdate = mealService.findById(mealToUpdate.getId());
        mealToUpdate.recalculateSummary();
        mealService.update(mealToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/meal")
    public ResponseEntity<Meal> deleteAllMeals() {
        mealService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/meal/{id}")
    public ResponseEntity<Meal> deleteMeal(@PathVariable("id") long id) {
        Meal meal = mealService.findById(id);
        mealService.delete(meal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

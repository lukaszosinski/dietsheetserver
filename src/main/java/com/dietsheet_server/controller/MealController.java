package com.dietsheet_server.controller;




import com.dietsheet_server.model.Meal;
import com.dietsheet_server.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class MealController {

    @Autowired
    Service<Meal> mealService;

    @GetMapping(value = "/meal/")
    public ResponseEntity<List<Meal>> getAllMeals() {
        List<Meal> meals = mealService.findAll();
        if(meals.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(meals, HttpStatus.OK);
    }

    @GetMapping(value = "/meal/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> getMeal(@PathVariable("id") long id) {
        Meal meal = mealService.findById(id);
        if (meal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(meal, HttpStatus.OK);
    }

    @PostMapping(value = "/meal/")
    public ResponseEntity<Meal> createMeal(@RequestBody Meal meal) {

        if (mealService.isExist(meal)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        mealService.save(meal);
        return new ResponseEntity<>(meal, HttpStatus.CREATED);
    }

    @PutMapping(value = "/meal/{id}")
    public ResponseEntity<Meal> updateMeal(@PathVariable("id") long id, @RequestBody Meal meal) {

        Meal mealToUpdate = mealService.findById(id);

        if (mealToUpdate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        mealToUpdate.setName(meal.getName());
        mealToUpdate.updateIngredients(meal.getIngredients());

        mealService.update(mealToUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/meal/")
    public ResponseEntity<Meal> deleteAllMeals() {
        mealService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/meal/{id}")
    public ResponseEntity<Meal> deleteMeal(@PathVariable("id") long id) {
        Meal meal = mealService.findById(id);
        if (meal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        mealService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

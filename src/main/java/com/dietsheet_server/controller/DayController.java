package com.dietsheet_server.controller;




import com.dietsheet_server.model.Day;
import com.dietsheet_server.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DayController {

    @Autowired
    Service<Day> dayService;

    @GetMapping(value = "/day/")
    public ResponseEntity<List<Day>> getAllDays() {
        List<Day> days = dayService.findAll();
        if(days.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(days, HttpStatus.OK);
    }

    @PostMapping(value = "/day/")
    public ResponseEntity<Day> createMeal(@RequestBody Day day) {

        if (dayService.isExist(day)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        dayService.save(day);
        return new ResponseEntity<>(day, HttpStatus.CREATED);
    }

    @GetMapping(value = "/day/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Day> getDay(@PathVariable("id") long id) {
        Day day = dayService.findById(id);
        if (day == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(day, HttpStatus.OK);
    }

    @PutMapping(value = "/day/{id}")
    public ResponseEntity<Day> updateDay(@PathVariable("id") long id, @RequestBody Day day) {
        Day dayToUpdate = dayService.findById(id);

        if (dayToUpdate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        dayToUpdate.setMeals(day.getMeals());
        dayService.update(dayToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/day/{id}")
    public ResponseEntity<Day> deleteMeal(@PathVariable("id") long id) {
        Day day = dayService.findById(id);
        if (day == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dayService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

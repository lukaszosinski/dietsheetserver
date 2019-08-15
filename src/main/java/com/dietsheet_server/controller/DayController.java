package com.dietsheet_server.controller;

import com.dietsheet_server.model.diet.Day;
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
public class DayController {

    @Autowired
    Service<Day> dayService;

    @GetMapping(value = "/day")
    public ResponseEntity<List<Day>> getAllDays(@AuthenticationPrincipal User user) {
        List<Day> days = dayService.findAll(user);
        return new ResponseEntity<>(days, HttpStatus.OK);
    }

    @PostMapping(value = "/day")
    public ResponseEntity<Day> createDay(
            @RequestBody Day day,
            @AuthenticationPrincipal User user) {
        dayService.save(day, user);
        return new ResponseEntity<>(day, HttpStatus.CREATED);
    }

    @GetMapping(value = "/day/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Day> getDay(@PathVariable("id") long id) {
        Day day = dayService.findById(id);
        return new ResponseEntity<>(day, HttpStatus.OK);
    }

    @PutMapping(value = "/day/{id}")
    public ResponseEntity<Day> updateDay(
            @PathVariable("id") long id,
            @RequestBody Day dayUpdateData) {
        dayService.update(dayUpdateData, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/day/{id}")
    public ResponseEntity<Day> deleteDay(@PathVariable("id") long id) {
        dayService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

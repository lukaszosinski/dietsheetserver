package com.dietsheet_server.controller;

import com.dietsheet_server.model.diet.Day;
import com.dietsheet_server.model.User;
import com.dietsheet_server.service.DayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class DayController {

    @Autowired
    DayService dayService;

    @GetMapping(value = "/day")
    public ResponseEntity<List<Day>> getDaysByDateInRange(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam LocalDate dateFrom,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam LocalDate dateTo,
            @AuthenticationPrincipal User user) {
        List<Day> days = dayService.getDaysByDateInRange(dateFrom, dateTo, user);
        return new ResponseEntity<>(days, HttpStatus.OK);
    }

    @GetMapping(value = "/day/byDate")
    public ResponseEntity<Day>getDayByDate(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam LocalDate date,
            @AuthenticationPrincipal User user) {
        Day day = dayService.getDayByDate(date, user);
        return new ResponseEntity<>(day, HttpStatus.OK);
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

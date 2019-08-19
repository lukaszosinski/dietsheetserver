package com.dietsheet_server.service;

import com.dietsheet_server.model.User;
import com.dietsheet_server.model.diet.Day;


import java.time.LocalDate;
import java.util.List;


public interface DayService extends Service<Day> {
    Day findByDate(LocalDate date, User user);
    List<Day> findDaysFromTo(LocalDate dateFrom, LocalDate dateTo, User user);
}

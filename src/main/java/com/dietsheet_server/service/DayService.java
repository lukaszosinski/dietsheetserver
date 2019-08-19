package com.dietsheet_server.service;

import com.dietsheet_server.model.User;
import com.dietsheet_server.model.diet.Day;


import java.time.LocalDate;


public interface DayService extends Service<Day> {
    Day findByDate(LocalDate date, User user);
}

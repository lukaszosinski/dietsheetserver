package com.dietsheet_server.service;


import com.dietsheet_server.DAO.DayDAO;
import com.dietsheet_server.model.diet.Day;
import com.dietsheet_server.model.User;
import com.dietsheet_server.model.diet.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service("dayService")
@Transactional
public class DayServiceImpl implements Service<Day> {

    @Autowired
    private DayDAO dayDAO;

    @Autowired
    private Service<Meal> mealService;

    @Override
    public Day findById(long id) {
        Day day = dayDAO.get(id);
        if(day == null) {
            throw new ResourceNotFoundException();
        }

        return day;
    }

    @Override
    public void save(Day day) {
        if(isExist(day)) {
            throw new DataIntegrityViolationException("Resource exists");
        }
        if(day.getMeals().size() > 0) {
            day.setMeals(getInitializedMeals(day.getMeals()));
            day.recalculateSummary();
        }
        dayDAO.save(day);
    }

    @Override
    public void save(Day day, User owner) {
        day.setOwner(owner);
        save(day);
    }

    @Override
    public void update(Day dayUpdateData, long id) {
        Day dayToUpdate = findById(id);
        dayToUpdate.setMeals(getInitializedMeals(dayUpdateData.getMeals()));
        dayDAO.update(dayUpdateData);
    }

    @Override
    public void delete(Day day) {
       dayDAO.delete(day);
    }

    @Override
    public void delete(long id) {
        Day day = findById(id);
        delete(day);
    }

    @Override
    public List<Day> findAll() {
        return dayDAO.getAll();
    }

    @Override
    public List<Day> findAll(List<Long> ids) {
        return dayDAO.getByIds(ids);
    }

    @Override
    public List<Day> findAll(User user) {
        return dayDAO.getAllByUser(user);
    }

    @Override
    public void deleteAll() {
        dayDAO.deleteAll();
    }

    @Override
    public boolean isExist(Day day) {
        try {
            dayDAO.get(day.getId());
            return true;
        } catch (ResourceNotFoundException e) {
            return false;
        }
    }

    public List<Meal> getInitializedMeals(List<Meal> meals) {
        return meals
                .stream()
                .map(meal -> mealService.findById(meal.getId()))
                .collect(Collectors.toList());
    }
}


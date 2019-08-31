package com.dietsheet_server.service;


import com.dietsheet_server.DAO.DayDAO;
import com.dietsheet_server.model.diet.Day;
import com.dietsheet_server.model.User;
import com.dietsheet_server.model.diet.DayMeal;
import com.dietsheet_server.model.diet.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service("dayService")
@Transactional
public class DayServiceImpl implements DayService {

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
        if(day.getDayMeals().size() > 0) {
            day.updateDayMeals(getInitializedDayMeals(day.getDayMeals()));
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
        dayToUpdate.updateDayMeals(getInitializedDayMeals(dayUpdateData.getDayMeals()));
        dayToUpdate.recalculateSummary();
        dayDAO.update(dayToUpdate);
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
    public List<Day> findAll(User user, Map<String, String> params) {
        return dayDAO.getAllByUser(user, params);
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

    @Override
    public Day getDayByDate(LocalDate date, User user) {
        return dayDAO.getDayByDate(date, user);
    }

    @Override
    public List<Day> getDaysByDateInRange(LocalDate dateFrom, LocalDate dateTo, User user) {
        return dayDAO.getDaysByDateInRange(dateFrom, dateTo, user);
    }

    public List<DayMeal> getInitializedDayMeals(List<DayMeal> dayMeals) {
        return dayMeals
                .stream()
                .map(dayMeal -> new DayMeal(
                        mealService.findById(dayMeal.getMeal().getId()),
                        dayMeal.isEaten()
                        )
                )
                .collect(Collectors.toList());
    }

}


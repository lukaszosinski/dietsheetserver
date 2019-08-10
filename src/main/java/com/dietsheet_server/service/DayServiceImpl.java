package com.dietsheet_server.service;


import com.dietsheet_server.DAO.DayDAO;
import com.dietsheet_server.model.diet.Day;
import com.dietsheet_server.model.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service("dayService")
@Transactional
public class DayServiceImpl implements Service<Day> {

    @Autowired
    private DayDAO dayDAO;

    @Override
    @PostAuthorize("returnObject.getOwner().getUsername() == principal.getUsername()")
    public Day findById(long id) {
        Day day = dayDAO.get(id);
        if(day == null) {
            throw new ResourceNotFoundException();
        }
        Hibernate.initialize(day.getMeals());
        Hibernate.initialize(day.getSummary());
        return day;
    }

    @Override
    public void save(Day day) {
        dayDAO.save(day);
    }

    @Override
    public void update(Day day) {
        dayDAO.update(day);
    }

    @Override
    public void delete(Day day) {
       dayDAO.delete(day);
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
        return dayDAO.get(day.getId()) != null;
    }
}


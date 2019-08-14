package com.dietsheet_server.DAO;


import com.dietsheet_server.model.diet.Day;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

@Component("dayDAO")
public class DayDAO extends AbstractDAO<Day> {
    public DayDAO() {
        setClazz(Day.class);
    }

    @Override
    public void initializeEntityChildren(Day day) {
        Hibernate.initialize(day.getMeals());
        Hibernate.initialize(day.getSummary());
    }
}

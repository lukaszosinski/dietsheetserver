package com.dietsheet_server.DAO;


import com.dietsheet_server.model.User;
import com.dietsheet_server.model.diet.Day;
import org.hibernate.Hibernate;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.time.LocalDate;

@Component("dayDAO")
public class DayDAO extends AbstractOwnedEntitySecuredDAO<Day> {
    public DayDAO() {
        setClazz(Day.class);
    }

    public Day getByDateAndUser(LocalDate date, User user) {
        String hql = "from " + clazz.getName() + " c where c.owner = :owner and c.date = :date";
        Query query = entityManager.createQuery(hql);
        query.setParameter("owner", user);
        query.setParameter("date", date);
        try {
            Day day = (Day) query.getSingleResult();
            initializeEntityChildren(day);
            return day;
        } catch (NoResultException e) {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public void initializeEntityChildren(Day day) {
        Hibernate.initialize(day.getMeals());
        Hibernate.initialize(day.getSummary());
    }
}

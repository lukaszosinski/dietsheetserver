package com.dietsheet_server.DAO;


import com.dietsheet_server.model.User;
import com.dietsheet_server.model.diet.Day;
import org.hibernate.Hibernate;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Component("dayDAO")
public class DayDAO extends AbstractOwnedEntitySecuredDAO<Day> {
    public DayDAO() {
        setClazz(Day.class);
    }

    public Day getDayByDate(LocalDate date, User user) {
        String hql =
                "from " +
                clazz.getName() +
                " c where c.owner = :owner and c.date = :date";
        Query query = entityManager.createQuery(hql)
            .setParameter("owner", user)
            .setParameter("date", date);
        try {
            Day day = (Day) query.getSingleResult();
            initializeEntityChildren(day);
            return day;
        } catch (NoResultException e) {
            throw new ResourceNotFoundException();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Day> getDaysByDateInRange(LocalDate dateFrom, LocalDate dateTo, User user) {
        String hql =
                "from "
                + clazz.getName()
                + " c where c.owner = :owner and c.date between :dateFrom and :dateTo";
        List<Day> days = entityManager
                .createQuery(hql)
                .setParameter("owner", user)
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .getResultList();
        days.forEach(this::initializeEntityChildren);
        return days;
    }


    @Override
    public void initializeEntityChildren(Day day) {
        Hibernate.initialize(day.getDayMeals());
        day.getDayMeals().forEach(dayMeal ->
                Hibernate.initialize(dayMeal.getMeal())
        );
        Hibernate.initialize(day.getSummary());
    }

}

package com.dietsheet_server.DAO;


import com.dietsheet_server.model.Day;
import org.springframework.stereotype.Component;

@Component("dayDAO")
public class DayDAO extends AbstractDAO<Day> {
    public DayDAO() {
        setClazz(Day.class);
    }
}

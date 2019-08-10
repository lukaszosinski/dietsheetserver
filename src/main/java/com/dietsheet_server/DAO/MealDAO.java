package com.dietsheet_server.DAO;


import com.dietsheet_server.model.diet.Meal;
import org.springframework.stereotype.Component;

@Component("mealDAO")
public class MealDAO extends AbstractDAO<Meal> {
    public MealDAO() {
        setClazz(Meal.class);
    }
}

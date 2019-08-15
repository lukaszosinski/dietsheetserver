package com.dietsheet_server.DAO;


import com.dietsheet_server.model.diet.Meal;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

@Component("mealDAO")
public class MealDAO extends AbstractOwnedEntitySecuredDAO<Meal> {
    public MealDAO() {
        setClazz(Meal.class);
    }

    @Override
    public void initializeEntityChildren(Meal meal) {
        Hibernate.initialize(meal.getSummary());
        Hibernate.initialize(meal.getIngredients());
        meal.getIngredients().forEach(ingredient ->
                Hibernate.initialize(ingredient.getProduct())
        );
    }
}

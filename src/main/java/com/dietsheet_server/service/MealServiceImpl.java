package com.dietsheet_server.service;


import com.dietsheet_server.DAO.MealDAO;
import com.dietsheet_server.model.Meal;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service("mealService")
@Transactional
public class MealServiceImpl implements Service<Meal> {

    @Autowired
    private MealDAO mealDAO;

    @Override
    public Meal findById(long id) {
        Meal meal = mealDAO.get(id);
        if(meal == null) {
            return null;
        }
        Hibernate.initialize(meal.getIngredients());
        meal.getIngredients().forEach(ingredient ->
            Hibernate.initialize(ingredient.getProduct())
        );
        return meal;
    }

    @Override
    public Meal findByName(String name) {
        return null;
    }

    @Override
    public void save(Meal meal) {
        mealDAO.save(meal);
    }

    @Override
    public void update(Meal meal) {
        mealDAO.update(meal);
    }

    @Override
    public void deleteById(long id) {
        mealDAO.delete(id);
    }

    @Override
    public List<Meal> findAll() {
        return mealDAO.getAll();
    }

    @Override
    public void deleteAll() {
        mealDAO.deleteAll();
    }

    @Override
    public boolean isExist(Meal meal) {
        return mealDAO.get(meal.getId()) != null;
    }
}

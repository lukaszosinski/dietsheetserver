package com.dietsheet_server.service;


import com.dietsheet_server.DAO.MealDAO;
import com.dietsheet_server.model.Meal;
import com.dietsheet_server.model.Product;
import com.dietsheet_server.model.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service("mealService")
@Transactional
public class MealServiceImpl implements Service<Meal> {

    @Autowired
    private MealDAO mealDAO;

    @Override
    @PostAuthorize("returnObject.getOwner().getUsername() == principal.getUsername()")
    public Meal findById(long id) {
        Meal meal = mealDAO.get(id);
        if(meal == null) {
            throw new ResourceNotFoundException();
        }
        Hibernate.initialize(meal.getIngredients());
        meal.getIngredients().forEach(ingredient ->
            Hibernate.initialize(ingredient.getProduct())
        );
        return meal;
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
    public void delete(Meal meal) {
        mealDAO.delete(meal);
    }

    @Override
    public List<Meal> findAll() {
        return mealDAO.getAll();
    }

    @Override
    public List<Meal> findAll(User user) {
        return mealDAO.getAllByUser(user);
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

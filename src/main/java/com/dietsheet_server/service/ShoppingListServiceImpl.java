package com.dietsheet_server.service;

import com.dietsheet_server.builder.ShoppingListBuilder;
import com.dietsheet_server.model.ShoppingList;
import com.dietsheet_server.model.User;
import com.dietsheet_server.model.diet.Day;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service("shoppingListService")
@Transactional
public class ShoppingListServiceImpl implements ShoppingListService {

    @Autowired
    ShoppingListBuilder shoppingListBuilder;

    @Override
    public ShoppingList findById(long id) {
        return null;
    }

    @Override
    public void save(ShoppingList object) {

    }

    @Override
    public void update(ShoppingList object) {

    }


    @Override
    public void delete(ShoppingList entity) {

    }

    @Override
    public List<ShoppingList> findAll() {
        return null;
    }

    @Override
    public List<ShoppingList> findAll(List<Long> ids) {
        return null;
    }

    @Override
    public List<ShoppingList> findAll(User user) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean isExist(ShoppingList object) {
        return false;
    }

    @Override
    public ShoppingList generateShoppingListForDays(List<Day> dayList) {
        dayList.forEach(day ->
           day.getMeals().forEach(meal ->
                   meal.getIngredients().forEach(ingredient ->
                           shoppingListBuilder.addItem(ingredient)))
        );
        return shoppingListBuilder.build();
    }
}

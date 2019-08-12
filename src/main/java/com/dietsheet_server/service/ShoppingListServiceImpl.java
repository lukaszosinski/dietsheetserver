package com.dietsheet_server.service;

import com.dietsheet_server.DAO.ShoppingListDAO;
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

    @Autowired
    ShoppingListDAO shoppingListDAO;

    @Override
    public ShoppingList findById(long id) {
        return null;
    }

    @Override
    public void save(ShoppingList shoppingList) {
        shoppingListDAO.save(shoppingList);
    }

    @Override
    public void update(ShoppingList shoppingList) {
        shoppingListDAO.update(shoppingList);
    }

    @Override
    public void delete(ShoppingList shoppingList) {
        shoppingListDAO.delete(shoppingList);
    }

    @Override
    public List<ShoppingList> findAll() {
        return shoppingListDAO.getAll();
    }

    @Override
    public List<ShoppingList> findAll(List<Long> ids) {
        return shoppingListDAO.getByIds(ids);
    }

    @Override
    public List<ShoppingList> findAll(User user) {
        return shoppingListDAO.getAllByUser(user);
    }

    @Override
    public void deleteAll() {
        shoppingListDAO.deleteAll();
    }

    @Override
    public boolean isExist(ShoppingList shoppingList) {
        return shoppingListDAO.get(shoppingList.getId()) != null;
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

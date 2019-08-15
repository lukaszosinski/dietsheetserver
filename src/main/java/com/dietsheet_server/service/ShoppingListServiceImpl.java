package com.dietsheet_server.service;

import com.dietsheet_server.DAO.DayDAO;
import com.dietsheet_server.DAO.ShoppingListDAO;
import com.dietsheet_server.model.diet.shoppinglist.ShoppingListBuilder;
import com.dietsheet_server.model.diet.shoppinglist.ShoppingList;
import com.dietsheet_server.model.User;
import com.dietsheet_server.model.diet.Day;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service("shoppingListService")
@Transactional
public class ShoppingListServiceImpl implements ShoppingListService {

    @Autowired
    ShoppingListBuilder shoppingListBuilder;

    @Autowired
    ShoppingListDAO shoppingListDAO;

    @Autowired
    Service<Day> dayService;

    @Override
    public ShoppingList findById(long id) {
        return shoppingListDAO.get(id);
    }

    @Override
    public void save(ShoppingList shoppingList) {
        if(isExist(shoppingList)) {
            throw new DataIntegrityViolationException("Resource exists");
        }
        shoppingListDAO.save(shoppingList);
    }

    @Override
    public void save(ShoppingList shoppingList, User owner) {
        shoppingList.setOwner(owner);
        save(shoppingList);
    }

    @Override
    public void update(ShoppingList shoppingListUpdateData, long id) {
        ShoppingList shoppingListToUpdate = findById(id);
        shoppingListToUpdate.updateItems(shoppingListUpdateData.getItems());
        shoppingListDAO.update(shoppingListToUpdate);
    }

    @Override
    public void delete(ShoppingList shoppingList) {
        shoppingListDAO.delete(shoppingList);
    }

    @Override
    public void delete(long id) {
        ShoppingList shoppingList = findById(id);
        delete(shoppingList);
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
        try {
            shoppingListDAO.get(shoppingList.getId());
            return true;
        } catch (ResourceNotFoundException e) {
            return false;
        }
    }

    @Override
    public ShoppingList generateShoppingListForDays(List<Long> dayIds) {
        List<Day> days = dayService.findAll(dayIds);
        days.forEach(day ->
           day.getMeals().forEach(meal ->
                   meal.getIngredients().forEach(ingredient ->
                           shoppingListBuilder.addItem(ingredient)))
        );
        return shoppingListBuilder.build();
    }
}

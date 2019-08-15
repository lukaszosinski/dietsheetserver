package com.dietsheet_server.DAO;

import com.dietsheet_server.model.diet.shoppinglist.ShoppingList;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

@Component("shoppingListDAO")
public class ShoppingListDAO extends AbstractDAO<ShoppingList> {
    public ShoppingListDAO() {
        setClazz(ShoppingList.class);
    }

    @Override
    public ShoppingList get(long id) {
        ShoppingList shoppingList = super.get(id);
        Hibernate.initialize(shoppingList.getItems());
        shoppingList.getItems().forEach(item ->
                Hibernate.initialize(item.getProduct())
        );
        return shoppingList;
    }
}

package com.dietsheet_server.DAO;

import com.dietsheet_server.model.ShoppingList;
import org.springframework.stereotype.Component;

@Component("shoppingListDAO")
public class ShoppingListDAO extends AbstractDAO<ShoppingList> {
    public ShoppingListDAO() {
        setClazz(ShoppingList.class);
    }
}

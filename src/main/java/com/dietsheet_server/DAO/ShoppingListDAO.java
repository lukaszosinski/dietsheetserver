package com.dietsheet_server.DAO;

import com.dietsheet_server.model.diet.shoppinglist.ShoppingList;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

@Component("shoppingListDAO")
public class ShoppingListDAO extends AbstractSecuredOwnedEntityDAO<ShoppingList> {
    public ShoppingListDAO() {
        setClazz(ShoppingList.class);
    }

    @Override
    public void initializeEntityChildren(ShoppingList shoppingList) {
        Hibernate.initialize(shoppingList.getItems());
        shoppingList.getItems().forEach(item ->
                Hibernate.initialize(item.getProduct())
        );
    }
}

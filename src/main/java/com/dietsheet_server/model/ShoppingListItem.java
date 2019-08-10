package com.dietsheet_server.model;

import com.dietsheet_server.model.diet.Ingredient;
import com.dietsheet_server.model.diet.Product;

public class ShoppingListItem extends Ingredient {

    public ShoppingListItem() {
    }

    public ShoppingListItem(Product product, int amount) {
        super(product, amount);
    }


}

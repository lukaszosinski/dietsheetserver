package com.dietsheet_server.builder;

import com.dietsheet_server.model.ShoppingList;
import com.dietsheet_server.model.diet.Ingredient;

public interface ShoppingListBuilder {
    ShoppingListBuilder addItem(Ingredient ingredient);
    ShoppingList build();
}

package com.dietsheet_server.model.diet.shoppinglist;

import com.dietsheet_server.model.diet.Ingredient;

public interface ShoppingListBuilder {
    ShoppingListBuilder addItem(Ingredient ingredient);
    ShoppingList build();
}

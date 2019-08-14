package com.dietsheet_server.service;

import com.dietsheet_server.model.diet.shoppinglist.ShoppingList;

import java.util.List;

public interface ShoppingListService extends Service<ShoppingList> {
    ShoppingList generateShoppingListForDays(List<Long> dayIds);
}

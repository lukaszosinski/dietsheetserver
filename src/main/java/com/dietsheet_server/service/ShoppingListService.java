package com.dietsheet_server.service;

import com.dietsheet_server.model.user.User;
import com.dietsheet_server.model.diet.shoppinglist.ShoppingList;

import java.time.LocalDate;

public interface ShoppingListService extends Service<ShoppingList> {
    ShoppingList generateShoppingListForDateRange(LocalDate dateFrom, LocalDate dateTo, User user);
}

package com.dietsheet_server.model;


import java.util.ArrayList;
import java.util.List;

public class ShoppingList extends OwnedEntity {
    List<ShoppingListItem> items = new ArrayList<>();

    public ShoppingList() {
    }

    public ShoppingList(List<ShoppingListItem> items) {
        this.items = items;
    }

    public List<ShoppingListItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingListItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "items=" + items +
                '}';
    }
}

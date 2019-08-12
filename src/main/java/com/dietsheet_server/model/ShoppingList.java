package com.dietsheet_server.model;


import com.dietsheet_server.model.diet.Ingredient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "shoppinglist")
public class ShoppingList extends OwnedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JoinColumn(name = "shoppinglist_id")
    List<Ingredient> items = new ArrayList<>();

    public ShoppingList() {
    }

    public ShoppingList(List<Ingredient> items) {
        this.items = items;
    }

    public List<Ingredient> getItems() {
        return items;
    }

    public void setItems(List<Ingredient> items) {
        this.items = items;
    }

    public void updateItems(List<Ingredient> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}

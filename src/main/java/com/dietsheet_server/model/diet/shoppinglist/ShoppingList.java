package com.dietsheet_server.model.diet.shoppinglist;


import com.dietsheet_server.model.OwnedEntity;
import com.dietsheet_server.model.diet.Ingredient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
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

    public ShoppingList(List<Ingredient> items) {
        this.items = items;
    }

    public void updateItems(List<Ingredient> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
    }
}

package com.dietsheet_server.model.diet.shoppinglist;


import com.dietsheet_server.model.OwnedEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "shoppinglist")
public class ShoppingList extends OwnedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name = "";

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @JoinColumn(name = "shoppinglist_id")
    List<ShoppingListItem> items = new ArrayList<>();

    public ShoppingList(List<ShoppingListItem> items) {
        this.items = items;
    }

    public void updateItems(List<ShoppingListItem> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
    }
}

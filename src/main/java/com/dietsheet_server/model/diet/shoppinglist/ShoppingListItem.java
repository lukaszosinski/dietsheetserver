package com.dietsheet_server.model.diet.shoppinglist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "shoppinglistitem")
public class ShoppingListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "productname")
    private String productName;
    @Column(name = "amount")
    private int amount;
    @Column (name = "unit")
    private String unit;
    @Column (name = "checked")
    private boolean checked;

    public ShoppingListItem(String productName, int amount, String unit) {
        this.productName = productName;
        this.amount = amount;
        this.unit = unit;
        this.checked = false;
    }
}

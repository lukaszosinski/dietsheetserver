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

    public ShoppingListItem(String productName, int amount) {
        this.productName = productName;
        this.amount = amount;
    }
}

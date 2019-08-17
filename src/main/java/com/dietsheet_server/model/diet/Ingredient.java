package com.dietsheet_server.model.diet;

import com.dietsheet_server.model.diet.shoppinglist.ShoppingList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "ingredient")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH,
                    CascadeType.DETACH
            })
    @JoinColumn(name ="product_id")
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="meal_id")
    private Meal meal;

    @Column(name = "amount")
    private int amount;

    public Ingredient(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }
}

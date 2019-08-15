package com.dietsheet_server.model.diet;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="product_id")
    private Product product;

    @Column(name = "amount")
    private int amount;

    public Ingredient(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }
}

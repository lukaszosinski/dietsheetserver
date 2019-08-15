package com.dietsheet_server.model.diet;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name = "product")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product extends DietEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Ingredient> ingredients = new ArrayList<>();

    @JsonIgnore
    @Override
    public List<DietEntity> getParents() {
        return ingredients
                .stream()
                .map(Ingredient::getMeal)
                .collect(Collectors.toList());
    }

    public void addIngredientReference(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredientReference(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    @Override
    public void recalculateSummary() {
        //Do nothing
    }
}

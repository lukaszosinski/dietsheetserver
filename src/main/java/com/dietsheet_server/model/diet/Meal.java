package com.dietsheet_server.model.diet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "meal")
public class Meal extends DietEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
            )
    @JoinColumn(name = "meal_id")
    private List<Ingredient> ingredients;

    @JsonIgnore
    @ManyToMany(mappedBy = "meals")
    private List<Day> days = new ArrayList<>();

    public Meal() {
        super();
        setSummary(new Summary());
    }

    public void updateIngredients(List<Ingredient> newIngredients) {
        ingredients.forEach(ingredient ->
                ingredient.getProduct().removeIngredientReference(ingredient));
        ingredients.clear();
        newIngredients.forEach(ingredient ->
                ingredient.getProduct().addIngredientReference(ingredient));
        ingredients.addAll(newIngredients);
    }

    @JsonIgnore
    @Override
    public List<DietEntity> getParents() {
        return days
                .stream()
                .map(day -> (DietEntity) day)
                .collect(Collectors.toList());
    }

    @Override
    public void recalculateSummary() {
        Summary newSummary = new Summary();
        for (Ingredient ingredient: this.getIngredients()
             ) {
            Summary summaryToAdd = ingredient.getProduct().getSummary();
            double multiplier = (double) ingredient.getAmount() / 100;
            newSummary = newSummary.add(summaryToAdd, multiplier);
        }
        this.updateSummary(newSummary);
    }
}

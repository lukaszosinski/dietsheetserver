package com.dietsheet_server.model.diet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(mappedBy = "meals")
    private List<Day> days = new ArrayList<>();

    public Meal() {
        super();
        setSummary(new Summary());
    }

    public void updateIngredients(List<Ingredient> newIngredients) {
        this.ingredients.clear();
        this.ingredients.addAll(newIngredients);
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

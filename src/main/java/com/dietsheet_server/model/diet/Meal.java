package com.dietsheet_server.model.diet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
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
    private Set<Day> days = new HashSet<>();

    public Meal() {
        super();
        setSummary(new Summary());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
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

package com.dietsheet_server.model.diet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import java.time.LocalDate;

import java.util.HashSet;

import java.util.Set;

@Entity
@Table(name = "day")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Day extends DietEntity {

    @Id
    @Column(name = "day_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "day_meal",
            joinColumns = @JoinColumn(name = "day_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private Set<Meal> meals = new HashSet<>();

    public Day() {
        super();
        if(getSummary() == null) {
            setSummary(new Summary());
        }
        //TODO Decide what to do with date and find right way to set it.
        this.date = LocalDate.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

    public void updateMeals(Set<Meal> newMeals) {
        this.meals.clear();
        this.meals.addAll(newMeals);
    }

    @Override
    public void recalculateSummary() {
        Summary newSummary = new Summary();
        for (Meal meal: meals
             ) {
             Summary summaryToAdd = meal.getSummary();
             newSummary = newSummary.add(summaryToAdd);
        }
        this.updateSummary(newSummary);
    }
}

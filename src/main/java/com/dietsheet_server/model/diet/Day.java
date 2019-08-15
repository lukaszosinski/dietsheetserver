package com.dietsheet_server.model.diet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDate;

import java.util.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name = "day")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Day extends DietEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "day_meal",
            joinColumns = @JoinColumn(name = "day_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private List<Meal> meals = new ArrayList<>();

    public Day() {
        super();
        if(getSummary() == null) {
            setSummary(new Summary());
        }
        //TODO Decide what to do with date and find right way to set it.
        this.date = LocalDate.now();
    }

    @Override
    public List<DietEntity> getParents() {
        return null;
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

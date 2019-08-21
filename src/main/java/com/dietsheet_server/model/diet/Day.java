package com.dietsheet_server.model.diet;
import com.dietsheet_server.serializer.LocalDateEpochDeserializer;
import com.dietsheet_server.serializer.LocalDateEpochSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    @JsonSerialize(using = LocalDateEpochSerializer.class)
    @JsonDeserialize(using = LocalDateEpochDeserializer.class)
    private LocalDate date;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JoinColumn(name = "day_id")
    private List<DayMeal> dayMeals = new ArrayList<>();

    @OneToOne(cascade =
            CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JoinColumn(
            name = "eaten_meals_summary_id",
            referencedColumnName = "id",
            unique = true
    )
    Summary eatenMealsSummary;

    public Day() {
        super();
        if(getSummary() == null) {
            setSummary(new Summary());
        }
        eatenMealsSummary = new Summary();
        //TODO Decide what to do with date and find right way to set it.
        this.date = LocalDate.now();
    }

    public void updateDayMeals(List<DayMeal> newDayMeals) {
        dayMeals.clear();
        dayMeals.addAll(newDayMeals);
    }

    @JsonIgnore
    @Override
    public List<DietEntity> getParents() {
        return null;
    }

    @Override
    public void recalculateSummary() {
        Summary newSummary = new Summary();
        Summary newEatenMealsSummary = new Summary();
        for (DayMeal dayMeal: dayMeals
             ) {
             Summary summaryToAdd = dayMeal.getMeal().getSummary();
             newSummary = newSummary.add(summaryToAdd);
             if(dayMeal.isEaten()) {
                 newEatenMealsSummary = newEatenMealsSummary.add(summaryToAdd);
             }
        }
        this.updateSummary(newSummary);
        this.updateEatenMealsSummary(newEatenMealsSummary);
    }

    private void updateEatenMealsSummary(Summary newEatenMealsSummary) {
        newEatenMealsSummary.setId(eatenMealsSummary.getId());
        eatenMealsSummary = newEatenMealsSummary;
    }
}

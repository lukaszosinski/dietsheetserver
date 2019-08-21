package com.dietsheet_server.model.diet;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "daymeal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DayMeal {
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
    @JoinColumn(name ="meal_id")
    private Meal meal;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="day_id")
    private Day day;

    @Column(name = "eaten")
    private boolean eaten;

    public DayMeal(Meal meal, boolean eaten) {
        this.meal = meal;
        this.eaten = eaten;
    }
}

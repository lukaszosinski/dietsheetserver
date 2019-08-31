package com.dietsheet_server.model.diet;


import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
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

    @Column(name = "granularity")
    @Enumerated(EnumType.STRING)
    private Granularity granularity = Granularity.HUNDRED_GRAMS;

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

    @Override

    public void recalculateSummary() {
        //Do nothing
    }

    @Getter
    public enum Granularity {
        HUNDRED_GRAMS(100),
        PIECE(1),
        HUNDRED_MILLILITERS(100),
        KILOGRAM(1);

        private final int amountDivider;

        Granularity(int amountDivider) {
            this.amountDivider = amountDivider;
        }
    }
}




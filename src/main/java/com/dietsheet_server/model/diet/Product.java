package com.dietsheet_server.model.diet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

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

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    Set<Price> prices = new HashSet<>();

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

    public void updatePrices(Set<Price> newPrices) {
        this.prices.clear();
        this.prices.addAll(newPrices);
    }

    @Getter
    public enum Granularity {
        HUNDRED_GRAMS(100),
        PIECE(1),
        HUNDRED_MILLILITERS(100);

        private final int amountDivider;

        Granularity(int amountDivider) {
            this.amountDivider = amountDivider;
        }
    }
}




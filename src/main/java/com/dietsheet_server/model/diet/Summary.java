package com.dietsheet_server.model.diet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "summary")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Summary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "kcal")
    private int kcal;

    @Column(name = "proteins")
    private int proteins;

    @Column(name = "carbs")
    private int carbs;

    @Column(name = "fat")
    private int fat;

    @Column(name = "roughage")
    private int roughage;

    public Summary() {
        this.kcal = 0;
        this.proteins = 0;
        this.carbs = 0;
        this.fat = 0;
        this.roughage = 0;
    }

    public Summary(int kcal, int proteins, int carbs, int fat, int roughage) {
        this.kcal = kcal;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fat = fat;
        this.roughage = roughage;
    }

    public Summary add(Summary summaryToAdd) {
        return new Summary(
                this.getKcal()         + summaryToAdd.getKcal()  ,
                this.getProteins()  + summaryToAdd.getProteins(),
                this.getCarbs()       + summaryToAdd.getCarbs() ,
                this.getFat()           + summaryToAdd.getFat()  ,
                this.getRoughage() + summaryToAdd.getRoughage()
        );
    }

    public Summary add(Summary summaryToAdd, double multiplier) {
        return new Summary(
                (int) (this.getKcal()        + summaryToAdd.getKcal()     * multiplier),
                (int) (this.getProteins()    + summaryToAdd.getProteins() * multiplier),
                (int) (this.getCarbs()       + summaryToAdd.getCarbs()    * multiplier),
                (int) (this.getFat()         + summaryToAdd.getFat()      * multiplier),
                (int) (this.getRoughage()    + summaryToAdd.getRoughage() * multiplier)
        );
    }
}

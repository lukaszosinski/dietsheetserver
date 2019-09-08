package com.dietsheet_server.model.diet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "summary")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Summary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "kcal")
    private double kcal;

    @Column(name = "proteins")
    private double proteins;

    @Column(name = "carbs")
    private double carbs;

    @Column(name = "fat")
    private double fat;

    @Column(name = "roughage")
    private double roughage;

    public Summary() {
        this.kcal = 0;
        this.proteins = 0;
        this.carbs = 0;
        this.fat = 0;
        this.roughage = 0;
    }

    @Builder
    public Summary(double kcal, double proteins, double carbs, double fat, double roughage) {
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
        Summary newSummary = new Summary(
                (this.getKcal()        + summaryToAdd.getKcal()     * multiplier),
                (this.getProteins()    + summaryToAdd.getProteins() * multiplier),
                (this.getCarbs()       + summaryToAdd.getCarbs()    * multiplier),
                (this.getFat()         + summaryToAdd.getFat()      * multiplier),
                (this.getRoughage()    + summaryToAdd.getRoughage() * multiplier));
        newSummary.roundValues(1);
        return newSummary;
    }

    public void roundValues(int precision) {
        double scale = Math.pow(10, precision);
        kcal = Math.round(kcal * scale) / scale;
        proteins = Math.round(proteins * scale) / scale;
        carbs = Math.round(carbs * scale) / scale;
        fat = Math.round(fat * scale) / scale;
        roughage = Math.round(roughage * scale) / scale;
    }
}

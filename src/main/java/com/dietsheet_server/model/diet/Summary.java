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

    @Column(name = "sugar")
    private double sugar;

    @Column(name = "fat")
    private double fat;

    @Column(name = "roughage")
    private double roughage;

    @Column(name = "potassium")
    private double potassium;

    @Column(name = "calcium")
    private double calcium;

    @Column(name = "vitaminD")
    private double vitaminD;

    @Column(name = "vitaminC")
    private double vitaminC;

    public Summary() {
        this.kcal = 0;
        this.proteins = 0;
        this.carbs = 0;
        this.sugar = 0;
        this.fat = 0;
        this.roughage = 0;
        this.potassium = 0;
        this.calcium = 0;
        this.vitaminD = 0;
        this.vitaminC = 0;

    }

    @Builder
    public Summary(
            double kcal,
            double proteins,
            double carbs,
            double sugar,
            double fat,
            double roughage,
            double potassium,
            double calcium,
            double vitaminD,
            double vitaminC) {
        this.kcal = kcal;
        this.proteins = proteins;
        this.carbs = carbs;
        this.sugar = sugar;
        this.fat = fat;
        this.roughage = roughage;
        this.potassium = potassium;
        this.calcium = calcium;
        this.vitaminD = vitaminD;
        this.vitaminC = vitaminC;
    }

    public Summary add(Summary summaryToAdd) {
         Summary newSummary = new Summary(
                this.getKcal()          + summaryToAdd.getKcal(),
                this.getProteins()   + summaryToAdd.getProteins(),
                this.getCarbs()        + summaryToAdd.getCarbs(),
                 this.getSugar()        + summaryToAdd.getSugar(),
                this.getFat()            + summaryToAdd.getFat(),
                this.getRoughage()  + summaryToAdd.getRoughage(),
                this.getPotassium() + summaryToAdd.getPotassium(),
                this.getCalcium()     + summaryToAdd.getCalcium(),
                this.getVitaminD()   + summaryToAdd.getVitaminD(),
                this.getVitaminC()   + summaryToAdd.getVitaminC());
         newSummary.roundValues(1);
         return newSummary;
    }

    public Summary add(Summary summaryToAdd, double multiplier) {
        Summary newSummary = new Summary(
                (this.getKcal()         + summaryToAdd.getKcal()      * multiplier),
                (this.getProteins()     + summaryToAdd.getProteins()  * multiplier),
                (this.getCarbs()        + summaryToAdd.getCarbs()     * multiplier),
                (this.getSugar()        + summaryToAdd.getSugar()     * multiplier),
                (this.getFat()          + summaryToAdd.getFat()       * multiplier),
                (this.getRoughage()     + summaryToAdd.getRoughage()  * multiplier),
                (this.getPotassium()    + summaryToAdd.getPotassium() * multiplier),
                (this.getCalcium()      + summaryToAdd.getCalcium()   * multiplier),
                (this.getVitaminD()     + summaryToAdd.getVitaminD()  * multiplier),
                (this.getVitaminC()     + summaryToAdd.getVitaminC()  * multiplier));
        newSummary.roundValues(1);
        return newSummary;
    }

    public void roundValues(int precision) {
        double scale = Math.pow(10, precision);
        kcal      = Math.round(kcal      * scale) / scale;
        proteins  = Math.round(proteins  * scale) / scale;
        carbs     = Math.round(carbs     * scale) / scale;
        sugar     = Math.round(sugar     * scale) / scale;
        fat       = Math.round(fat       * scale) / scale;
        roughage  = Math.round(roughage  * scale) / scale;
        potassium = Math.round(potassium * scale) / scale;
        calcium   = Math.round(calcium   * scale) / scale;
        vitaminD  = Math.round(vitaminD  * scale) / scale;
        vitaminC  = Math.round(vitaminC  * scale) / scale;
    }
}

package com.dietsheet_server.model.diet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getRoughage() {
        return roughage;
    }

    public void setRoughage(int roughage) {
        this.roughage = roughage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Summary)) return false;
        Summary summary = (Summary) o;
        return getId() == summary.getId() &&
                getKcal() == summary.getKcal() &&
                getProteins() == summary.getProteins() &&
                getCarbs() == summary.getCarbs() &&
                getFat() == summary.getFat() &&
                getRoughage() == summary.getRoughage();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getKcal(), getProteins(), getCarbs(), getFat(), getRoughage());
    }
}

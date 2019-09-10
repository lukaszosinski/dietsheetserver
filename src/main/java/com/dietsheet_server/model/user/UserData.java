package com.dietsheet_server.model.user;

import com.dietsheet_server.serializer.LocalDateEpochDeserializer;
import com.dietsheet_server.serializer.LocalDateEpochSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "user_data")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "birth_date")
    @JsonSerialize(using = LocalDateEpochSerializer.class)
    @JsonDeserialize(using = LocalDateEpochDeserializer.class)
    LocalDate birthDate;


    @Column(name = "height")
    private double height;

    @Column(name = "weight")
    private double weight;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "historical_data_id")
    private List<UserDataSnapshot> historicalData = new ArrayList<>();

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(name = "bmi_status")
    @Enumerated(EnumType.STRING)
    private BMIStatus bmiStatus;

    @Column(name = "physical_activity")
    @Enumerated(EnumType.STRING)
    private PhysicalActivity physicalActivity;

    @Column(name = "date")
    @JsonSerialize(using = LocalDateEpochSerializer.class)
    @JsonDeserialize(using = LocalDateEpochDeserializer.class)
    LocalDate date;

    public UserData() {
        birthDate = LocalDate.now();
        height = 0;
        weight = 0;
        sex = Sex.UNDEFINED;
        bmiStatus = BMIStatus.UNDEFINED;
        physicalActivity = PhysicalActivity.LOW;
        date = LocalDate.now();
    }

    public void calculateBMI() {
        if(height > 0.0 && weight > 0.0) {
            double bmi = weight / ((height/100.0)*(height/100.0));
            setBmiStatus(getBMIStatusForBMI(bmi));
        }
    }

    public void updateAndStoreUserData(UserData newUserData) {
        historicalData.add(new UserDataSnapshot(this));
        date = LocalDate.now();
        birthDate = newUserData.getBirthDate();
        height = newUserData.getHeight();
        weight = newUserData.getWeight();
        sex = newUserData.getSex();
        physicalActivity = newUserData.getPhysicalActivity();
        calculateBMI();
    }

    @JsonIgnore
    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private BMIStatus getBMIStatusForBMI(double bmi) {
        if(bmi < 18.5) {
            return BMIStatus.UNDERWEIGHT;
        } else if (bmi >= 18.5 && bmi < 25.0) {
            return BMIStatus.CORRECT_WEIGHT;
        } else if (bmi >= 25.0 && bmi < 30) {
            return BMIStatus.OVERWEIGHT;
        } else {
            return BMIStatus.OBESITY;
        }
    }

    public enum Sex {
        MALE,
        FEMALE,
        UNDEFINED
    }

    public enum BMIStatus {
        UNDERWEIGHT,
        CORRECT_WEIGHT,
        OVERWEIGHT,
        OBESITY,
        UNDEFINED
    }

    @Getter
    public enum  PhysicalActivity {
        VERY_LOW (1.2),
        LOW(1.35),
        MEDIUM(1.55),
        HIGH(1.75),
        VERY_HIGH(2.0);

        double cprIndicator;

        PhysicalActivity(double cprIndicator) {
            this.cprIndicator = cprIndicator;
        }
    }
}


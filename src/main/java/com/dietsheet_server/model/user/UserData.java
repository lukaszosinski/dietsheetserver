package com.dietsheet_server.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "user_data")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "age")
    private int age;

    @Column(name = "height")
    private double height;

    @Column(name = "weight")
    private double weight;

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @JsonIgnore
    @Column(name = "bmi")
    private double bmi;

    @Column(name = "bmi_status")
    @Enumerated(EnumType.STRING)
    private BMIStatus bmiStatus;

    private void calculateBMI() {
        if(height > 0 && weight > 0) {
            bmi = weight / (height*height);
            setBmiStatus(getBMIStatusForBMI(bmi));
        }
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
        FEMALE
    }

    public enum BMIStatus {
        UNDERWEIGHT,
        CORRECT_WEIGHT,
        OVERWEIGHT,
        OBESITY
    }
}

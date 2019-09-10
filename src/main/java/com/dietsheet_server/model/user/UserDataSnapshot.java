package com.dietsheet_server.model.user;

import com.dietsheet_server.serializer.LocalDateEpochDeserializer;
import com.dietsheet_server.serializer.LocalDateEpochSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "user_data_snapshot")
public class UserDataSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "date", nullable = false)
    @JsonSerialize(using = LocalDateEpochSerializer.class)
    @JsonDeserialize(using = LocalDateEpochDeserializer.class)
    LocalDate date;

    @Column(name = "height")
    private double height;

    @Column(name = "weight")
    private double weight;

    @Column(name = "bmi_status")
    @Enumerated(EnumType.STRING)
    private UserData.BMIStatus bmiStatus;

    @Column(name = "physical_activity")
    @Enumerated(EnumType.STRING)
    private UserData.PhysicalActivity physicalActivity;

    UserDataSnapshot(UserData userData) {
        date = userData.getDate();
        height = userData.getHeight();
        weight = userData.getWeight();
        bmiStatus = userData.getBmiStatus();
        physicalActivity = userData.getPhysicalActivity();
    }
}

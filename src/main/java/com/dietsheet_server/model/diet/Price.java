package com.dietsheet_server.model.diet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "price")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    String name;

    @Column(name = "amount")
    int amount;

    @Column(name = "unit")
    String unit;
}

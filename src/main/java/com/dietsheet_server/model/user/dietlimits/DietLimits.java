package com.dietsheet_server.model.user.dietlimits;

import com.dietsheet_server.model.diet.Summary;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "diet_limits")
public class DietLimits {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "min_limits")
    private Summary minLimits = null;

    @Column(name = "max_limits")
    private Summary maxLimits = null;
}

package com.dietsheet_server.model.user;

import com.dietsheet_server.model.user.dietlimits.DietLimitsCalculationStrategyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode
@Data
@Entity
@Table(name = "user_preferences")
public class UserPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "strategy_enum")
    @Enumerated(EnumType.STRING)
    private DietLimitsCalculationStrategyEnum strategyEnum = DietLimitsCalculationStrategyEnum.MANUAL;
}

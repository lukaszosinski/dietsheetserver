package com.dietsheet_server.model.user.dietlimits;

import com.dietsheet_server.model.diet.Summary;
import com.dietsheet_server.model.user.UserData;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_diet_limits")
public class UserDietLimits {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "min_limits")
    private Summary minLimits = null;

    @Column(name = "max_limits")
    private Summary maxLimits = null;

    @Column(name = "strategy_enum")
    @Enumerated(EnumType.STRING)
    private DietLimitsCalculationStrategyEnum strategyEnum;

    private DietLimitsCalculationStrategy dietLimitsCalculationStrategy;

    UserDietLimits() {
        this.dietLimitsCalculationStrategy = DietLimitsCalculationStrategyFactory.getDietLimitsCalculationStrategy(strategyEnum);
    }

    public void calculateDietLimits(UserData userData) {
        minLimits = dietLimitsCalculationStrategy.calculateMinLimits(userData);
        maxLimits = dietLimitsCalculationStrategy.calculateMaxLimits(userData);
    }
}

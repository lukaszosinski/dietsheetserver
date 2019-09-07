package com.dietsheet_server.model.user;

import com.dietsheet_server.model.diet.Summary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDietLimits {
    private Summary minLimits = null;
    private Summary maxLimits = null;

    private DietLimitsCalculationStrategy dietLimitsCalculationStrategy;

    UserDietLimits(DietLimitsCalculationStrategy dietLimitsCalculationStrategy) {
        this.dietLimitsCalculationStrategy = dietLimitsCalculationStrategy;
    }

    public void calculateDietLimits(UserData userData) {
        minLimits = dietLimitsCalculationStrategy.calculateMinLimits(userData);
        maxLimits = dietLimitsCalculationStrategy.calculateMaxLimits(userData);
    }
}

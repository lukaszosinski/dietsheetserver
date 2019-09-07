package com.dietsheet_server.model.user.dietlimits;

import com.dietsheet_server.model.diet.Summary;
import com.dietsheet_server.model.user.UserData;

public class LoseWeightLimitsCalculationStrategy implements DietLimitsCalculationStrategy {
    @Override
    public Summary calculateMinLimits(UserData userData) {
        return null;
    }

    @Override
    public Summary calculateMaxLimits(UserData userData) {
        return null;
    }
}

package com.dietsheet_server.model.user;

import com.dietsheet_server.model.diet.Summary;

public interface DietLimitsCalculationStrategy {
    Summary calculateMinLimits(UserData userData);
    Summary calculateMaxLimits(UserData userData);
}

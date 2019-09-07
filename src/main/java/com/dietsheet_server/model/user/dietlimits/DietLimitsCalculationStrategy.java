package com.dietsheet_server.model.user.dietlimits;

import com.dietsheet_server.model.diet.Summary;
import com.dietsheet_server.model.user.UserData;

public interface DietLimitsCalculationStrategy {
    Summary calculateMinLimits(UserData userData);
    Summary calculateMaxLimits(UserData userData);
}

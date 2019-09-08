package com.dietsheet_server.model.user.dietlimits;

import com.dietsheet_server.model.user.UserData;

public interface DietLimitsCalculationStrategy {
    DietLimits calculateLimits(UserData userData);
}

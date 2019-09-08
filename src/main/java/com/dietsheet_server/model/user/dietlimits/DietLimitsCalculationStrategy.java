package com.dietsheet_server.model.user.dietlimits;

import com.dietsheet_server.model.user.UserData;

import java.util.HashMap;
import java.util.Map;

public interface DietLimitsCalculationStrategy {

    Map<UserData.PhysicalActivity, Double> CommonCPRIndicators
            = new HashMap<UserData.PhysicalActivity, Double>() {{
        put(UserData.PhysicalActivity.VERY_LOW, 1.2);
        put(UserData.PhysicalActivity.LOW, 1.35);
        put(UserData.PhysicalActivity.MEDIUM, 1.55);
        put(UserData.PhysicalActivity.HIGH, 1.75);
        put(UserData.PhysicalActivity.VERY_HIGH, 2.0);
    }};

    DietLimits calculateLimits(UserData userData);
}

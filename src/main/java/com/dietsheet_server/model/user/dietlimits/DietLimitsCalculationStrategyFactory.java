package com.dietsheet_server.model.user.dietlimits;

import java.util.HashMap;
import java.util.Map;

public class DietLimitsCalculationStrategyFactory {
    private static final Map<DietLimitsCalculationStrategyEnum, Class<? extends DietLimitsCalculationStrategy>> dlcs =
            new HashMap<DietLimitsCalculationStrategyEnum, Class<? extends DietLimitsCalculationStrategy>>() {{
               put(DietLimitsCalculationStrategyEnum.KEEP_WEIGHT, KeepWeightLimitsCalculationStrategy.class);
               put(DietLimitsCalculationStrategyEnum.LOSE_WEIGHT, LoseWeightLimitsCalculationStrategy.class);
               put(DietLimitsCalculationStrategyEnum.BUILD_MASS, BuildMassLimitsCalculationStrategy.class);
            }};

    public static DietLimitsCalculationStrategy getDietLimitsCalculationStrategy(
            DietLimitsCalculationStrategyEnum dietLimitsCalculationStrategyEnum) {
        DietLimitsCalculationStrategy instance = null;
        try {
             instance = dlcs.get(dietLimitsCalculationStrategyEnum).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }
}



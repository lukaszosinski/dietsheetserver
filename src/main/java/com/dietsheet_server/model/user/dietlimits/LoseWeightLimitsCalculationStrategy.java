package com.dietsheet_server.model.user.dietlimits;

import com.dietsheet_server.model.diet.Summary;
import com.dietsheet_server.model.user.UserData;
import com.dietsheet_server.model.user.dietlimits.brm.BRMCalculator;
import com.dietsheet_server.model.user.dietlimits.brm.MifflinStJeorBRMCalculator;

public class LoseWeightLimitsCalculationStrategy implements DietLimitsCalculationStrategy {

    @Override
    public DietLimits calculateLimits(UserData userData) {
        BRMCalculator brmCalculator = new MifflinStJeorBRMCalculator();
        double BRMKcal = brmCalculator.calculateKCAL(userData);

        final double LOSE_WEIGHT_KCAL_REDUCTION = 200.0;
        double kcal = BRMKcal * CommonCPRIndicators.get(userData.getPhysicalActivity()) - LOSE_WEIGHT_KCAL_REDUCTION;

        double proteins = userData.getWeight() * 2.2;
        double fat = (kcal * 0.2) / 9;
        double carbs = (kcal - (proteins * 4.0 + fat * 9))/4;
        double roughage = 35.0;

        Summary minLimits = new Summary();
        minLimits.setKcal(kcal * 0.7);
        minLimits.setProteins(proteins * 0.7);
        minLimits.setCarbs(carbs * 0.7);
        minLimits.setFat(fat * 0.7);
        minLimits.setRoughage(roughage * 0.9);
        minLimits.roundValues(2);

        Summary maxLimits = new Summary();
        maxLimits.setKcal(kcal);
        maxLimits.setProteins(proteins );
        maxLimits.setCarbs(carbs);
        maxLimits.setFat(fat);
        maxLimits.setRoughage(roughage * 1.3);
        maxLimits.roundValues(2);

        DietLimits dietLimits = new DietLimits();
        dietLimits.setMaxLimits(maxLimits);
        dietLimits.setMinLimits(minLimits);

        return dietLimits;
    }
}

package com.dietsheet_server.model.user.dietlimits;

import com.dietsheet_server.model.diet.Summary;
import com.dietsheet_server.model.user.UserData;
import com.dietsheet_server.model.user.dietlimits.brm.BRMCalculator;
import com.dietsheet_server.model.user.dietlimits.brm.MifflinStJeorBRMCalculator;

public class BuildMassLimitsCalculationStrategy implements DietLimitsCalculationStrategy {
    @Override
    public DietLimits calculateLimits(UserData userData) {
        BRMCalculator brmCalculator = new MifflinStJeorBRMCalculator();
        double BRMKcal = brmCalculator.calculateKCAL(userData);

        final double BUILD_MASS_KCAL_INCREASE = 200.0;
        double kcal = BRMKcal * CommonCPRIndicators.get(userData.getPhysicalActivity()) + BUILD_MASS_KCAL_INCREASE;

        double proteins = userData.getWeight() * 2.0;
        double fat = (kcal * 0.25) / 9;
        double carbs = (kcal - (proteins * 4.0 + fat * 9))/4;
        double roughage = 30.0;

        Summary minLimits = new Summary();
        minLimits.setKcal(kcal);
        minLimits.setProteins(proteins);
        minLimits.setCarbs(carbs);
        minLimits.setFat(fat);
        minLimits.setRoughage(roughage * 0.7);

        Summary maxLimits = new Summary();
        maxLimits.setKcal(kcal * 1.4);
        maxLimits.setProteins(proteins * 1.4);
        maxLimits.setCarbs(carbs * 1.4);
        maxLimits.setFat(fat * 1.4);
        maxLimits.setRoughage(roughage * 1.3);

        DietLimits dietLimits = new DietLimits();
        dietLimits.setMaxLimits(maxLimits);
        dietLimits.setMinLimits(minLimits);

        return dietLimits;
    }
}

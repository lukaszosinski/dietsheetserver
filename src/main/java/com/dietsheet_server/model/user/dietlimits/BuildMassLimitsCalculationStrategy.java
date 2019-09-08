package com.dietsheet_server.model.user.dietlimits;

import com.dietsheet_server.model.diet.Summary;
import com.dietsheet_server.model.user.UserData;
import com.dietsheet_server.model.user.dietlimits.brm.BRMCalculator;
import com.dietsheet_server.model.user.dietlimits.brm.MifflinStJeorBRMCalculator;

public class BuildMassLimitsCalculationStrategy implements DietLimitsCalculationStrategy {

    static final double BUILD_MASS_KCAL_INCREASE = 200.0;

    @Override
    public DietLimits calculateLimits(UserData userData) {
        BRMCalculator brmCalculator = new MifflinStJeorBRMCalculator();
        double BRMKcal = brmCalculator.calculateKCAL(userData);


        double kcal = BRMKcal * userData.getPhysicalActivity().getCprIndicator() + BUILD_MASS_KCAL_INCREASE;

        double proteins = userData.getWeight() * 2.0;
        double fat = (kcal * 0.25) / 9;
        double carbs = (kcal - (proteins * 4.0 + fat * 9))/4;
        double roughage = 30.0;

        Summary minLimits = Summary.builder()
                .kcal(kcal)
                .proteins(proteins)
                .carbs(carbs)
                .fat(fat)
                .roughage(roughage * 0.7)
                .build();
        minLimits.roundValues(2);

        Summary maxLimits = Summary.builder()
                .kcal(kcal * 1.4)
                .proteins(proteins * 1.4)
                .carbs(carbs * 1.4)
                .fat(fat * 1.4)
                .roughage(roughage * 1.3)
                .build();
        maxLimits.roundValues(2);

        DietLimits dietLimits = new DietLimits();
        dietLimits.setMaxLimits(maxLimits);
        dietLimits.setMinLimits(minLimits);

        return dietLimits;
    }
}

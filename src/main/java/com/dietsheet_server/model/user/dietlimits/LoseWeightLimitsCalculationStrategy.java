package com.dietsheet_server.model.user.dietlimits;

import com.dietsheet_server.model.diet.Summary;
import com.dietsheet_server.model.user.UserData;
import com.dietsheet_server.model.user.dietlimits.brm.BRMCalculator;
import com.dietsheet_server.model.user.dietlimits.brm.MifflinStJeorBRMCalculator;


public class LoseWeightLimitsCalculationStrategy implements DietLimitsCalculationStrategy {

    static final double LOSE_WEIGHT_KCAL_REDUCTION = 200.0;

    @Override
    public DietLimits calculateLimits(UserData userData) {
        BRMCalculator brmCalculator = new MifflinStJeorBRMCalculator();
        double BRMKcal = brmCalculator.calculateKCAL(userData);


        double kcal = BRMKcal * userData.getPhysicalActivity().getCprIndicator() - LOSE_WEIGHT_KCAL_REDUCTION;

        double proteins = userData.getWeight() * 2.2;
        double fat = (kcal * 0.2) / 9;
        double carbs = (kcal - (proteins * 4.0 + fat * 9))/4;
        double roughage = 35.0;

        Summary minLimits = Summary.builder()
                .kcal(kcal * 0.7)
                .proteins(proteins * 0.7)
                .carbs(carbs * 0.7)
                .fat(fat * 0.7)
                .roughage(roughage * 0.9)
                .build();
        minLimits.roundValues(2);

        Summary maxLimits = Summary.builder()
                .kcal(kcal)
                .proteins(proteins)
                .carbs(carbs)
                .fat(fat)
                .roughage(roughage)
                .build();
        maxLimits.roundValues(2);

        DietLimits dietLimits = new DietLimits();
        dietLimits.setMaxLimits(maxLimits);
        dietLimits.setMinLimits(minLimits);

        return dietLimits;
    }
}

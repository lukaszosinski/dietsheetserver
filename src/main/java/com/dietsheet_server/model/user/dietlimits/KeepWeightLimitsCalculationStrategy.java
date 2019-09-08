package com.dietsheet_server.model.user.dietlimits;

import com.dietsheet_server.model.diet.Summary;
import com.dietsheet_server.model.user.UserData;
import com.dietsheet_server.model.user.dietlimits.brm.BRMCalculator;
import com.dietsheet_server.model.user.dietlimits.brm.MifflinStJeorBRMCalculator;
import org.springframework.stereotype.Component;


@Component
public class KeepWeightLimitsCalculationStrategy implements DietLimitsCalculationStrategy {

    @Override
    public DietLimits calculateLimits(UserData userData) {
        BRMCalculator brmCalculator = new MifflinStJeorBRMCalculator();
        double BRMKcal = brmCalculator.calculateKCAL(userData);

        double kcal = BRMKcal * userData.getPhysicalActivity().getCprIndicator();
        double carbs = (kcal/2)/4;
        double proteins = (kcal/4)/4;
        double fat = (kcal/4)/9;
        double roughage = 30.0;

        Summary minLimits = Summary.builder()
                .kcal(kcal * 0.95)
                .proteins(proteins * 0.95)
                .carbs(carbs * 0.95)
                .fat(fat * 0.95)
                .roughage(roughage * 0.8)
                .build();
        minLimits.roundValues(2);

        Summary maxLimits = Summary.builder()
                .kcal(kcal * 1.05)
                .proteins(proteins * 1.05)
                .carbs(carbs * 1.05)
                .fat(fat * 1.05)
                .roughage(roughage * 1.2)
                .build();
        maxLimits.roundValues(2);

        DietLimits dietLimits = new DietLimits();
        dietLimits.setMaxLimits(maxLimits);
        dietLimits.setMinLimits(minLimits);

        return dietLimits;
    }
}

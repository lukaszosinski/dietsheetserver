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

        double kcal = BRMKcal * CommonCPRIndicators.get(userData.getPhysicalActivity());
        double carbs = (kcal/2)/4;
        double proteins = (kcal/4)/4;
        double fat = (kcal/4)/9;
        double roughage = 30.0;

        Summary minLimits = new Summary();
        minLimits.setKcal(kcal * 0.95);
        minLimits.setProteins(proteins * 0.95);
        minLimits.setCarbs(carbs * 0.95);
        minLimits.setFat(fat * 0.95);
        minLimits.setRoughage(roughage * 0.8);

        Summary maxLimits = new Summary();
        maxLimits.setKcal(kcal * 1.05);
        maxLimits.setProteins(proteins * 1.05);
        maxLimits.setCarbs(carbs * 1.05);
        maxLimits.setFat(fat * 1.05);
        maxLimits.setRoughage(roughage * 1.2);

        DietLimits dietLimits = new DietLimits();
        dietLimits.setMaxLimits(maxLimits);
        dietLimits.setMinLimits(minLimits);

        return dietLimits;
    }
}

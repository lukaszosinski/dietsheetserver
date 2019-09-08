package com.dietsheet_server.model.user.dietlimits.brm;

import com.dietsheet_server.model.user.UserData;

public class MifflinStJeorBRMCalculator implements BRMCalculator {
    @Override
    public double calculateKCAL(UserData userData) {
        double kcal = 9.99 * userData.getWeight() + 6.25 * userData.getHeight() - 4.92 * userData.getAge();
        if(userData.getSex() == UserData.Sex.MALE) {
            kcal += 5.0;
        } else if (userData.getSex() == UserData.Sex.FEMALE) {
            kcal -= 161.0;
        }
        return kcal;
    }
}

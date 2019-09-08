package com.dietsheet_server.model.user.dietlimits.brm;

import com.dietsheet_server.model.user.UserData;

public interface BRMCalculator {
    double calculateKCAL(UserData userData);
}

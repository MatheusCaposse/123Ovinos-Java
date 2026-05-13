package com.ovinos.entity.Enum;

import java.util.Arrays;
import java.util.List;

public enum Alert {
    ALERTA,
    NORMAL;

    public static List<Alert> getAlerts(){
        List<Alert> list = Arrays.asList(Alert.ALERTA, Alert.NORMAL);
        return list;
    }
}

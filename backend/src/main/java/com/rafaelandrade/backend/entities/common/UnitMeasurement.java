package com.rafaelandrade.backend.entities.common;

public enum UnitMeasurement {
    ML(0),
    L(1),
    G(2),
    KG(3);

    private int code;

    private UnitMeasurement(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static UnitMeasurement valueOf(int code) {
        for (UnitMeasurement value : UnitMeasurement.values()) {
            if (code == value.getCode()) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid UnitMeasurement code.");
    }
}
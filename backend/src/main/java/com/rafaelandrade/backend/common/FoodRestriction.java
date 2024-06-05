package com.rafaelandrade.backend.common;

public enum FoodRestriction {

    NONE(0),
    VEGAN(1),
    VEGETARIAN(2),
    LACTOSE_FREE(3),
    NO_SUGAR(4),
    ORGANIC(5);

    private int code;

    private FoodRestriction(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static FoodRestriction valueOf(int code) {
        for (FoodRestriction value : FoodRestriction.values()) {
            if (code == value.getCode()) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid FoodRestriction code.");
    }
}

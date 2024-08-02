package com.rafaelandrade.backend.entities.common;

public enum ResidenceType {
    HOUSE(0),
    APARTMENT(1);

    private int code;

    private ResidenceType(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ResidenceType valueOf(int code){
        for(ResidenceType value : ResidenceType.values()){
            if(code == value.getCode()){
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid ResidenceType code.");
    }

}

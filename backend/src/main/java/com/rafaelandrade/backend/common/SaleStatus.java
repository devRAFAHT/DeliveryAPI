package com.rafaelandrade.backend.common;

public enum SaleStatus {

    ACTIVATED(0),
    PAUSED(1);

    private int code;

    private SaleStatus(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static SaleStatus valueOf(int code){
        for(SaleStatus value : SaleStatus.values()){
            if(code == value.getCode()){
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid SaleStatus code.");
    }
}

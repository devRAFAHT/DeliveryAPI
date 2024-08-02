package com.rafaelandrade.backend.entities.common;

public enum PortionSize {

    SMALL(0),
    AVERAGE(1),
    BIG(2),
    FAMILY(3);

    private int code;

    private PortionSize(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PortionSize valueOf(int code){
        for(PortionSize value : PortionSize.values()){
            if(code == value.getCode()){
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid PortionSize code.");
    }

}

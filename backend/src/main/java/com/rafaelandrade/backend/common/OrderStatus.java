package com.rafaelandrade.backend.common;

public enum OrderStatus {

    WAITING_PAYMENT(0),
    WAITING_CONFIRMATION(1),
    IN_PREPARATION(2),
    WAITING_FOR_PICKUP_BY_DELIVERY_PERSON(3),
    WAITING_FOR_PICKUP_AT_LOCATION(4),
    IN_TRANSIT(5),
    CANCELED(6),
    DELIVERY_FAILED(7),
    ORDER_DELIVERED(8),
    REFUNDED(9);

    private int code;

    private OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OrderStatus valueOf(int code) {
        for (OrderStatus value : OrderStatus.values()) {
            if (code == value.getCode()) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatus code.");
    }
}

package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.common.OrderStatus;

import java.io.Serial;
import java.io.Serializable;

public class OrderUpdateDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private OrderStatus orderStatus;

    public OrderUpdateDTO(){
    }

    public OrderUpdateDTO(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}

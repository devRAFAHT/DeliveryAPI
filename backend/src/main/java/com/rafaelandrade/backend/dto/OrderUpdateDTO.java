package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.common.OrderStatus;

import java.io.Serial;
import java.io.Serializable;

public class OrderUpdateDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private OrderStatus orderStatus;

    public OrderUpdateDTO(){
    }

    public OrderUpdateDTO(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}

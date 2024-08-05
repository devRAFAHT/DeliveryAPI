package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.common.OrderStatus;
import com.rafaelandrade.backend.entities.Item;
import com.rafaelandrade.backend.entities.Order;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderDTO {

    private Long id;
    private Instant createdAt;
    private Instant canceledAt;
    private BigDecimal subTotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalPrice;
    private Duration estimatedDeliveryTime;
    private String specialRequest;
    private OrderStatus orderStatus;
    private UserDTO client;
    private LegalEntityDTO legalEntity;
    private List<ItemDTO> items = new ArrayList<>();

    public OrderDTO() {
    }

    public OrderDTO(Long id, BigDecimal deliveryFee, String specialRequest, UserDTO client, LegalEntityDTO legalEntity) {
        this.id = id;
        this.deliveryFee = deliveryFee;
        this.specialRequest = specialRequest;
        this.client = client;
        this.legalEntity = legalEntity;
    }

    public OrderDTO(Order orderEntity) {
        this.id = orderEntity.getId();
        this.createdAt = orderEntity.getCreatedAt();
        this.canceledAt = orderEntity.getCanceledAt();
        this.subTotal = orderEntity.getSubTotal();
        this.deliveryFee = orderEntity.getDeliveryFee();
        this.totalPrice = orderEntity.getTotalPrice();
        this.estimatedDeliveryTime = orderEntity.getEstimatedDeliveryTime();
        this.specialRequest = orderEntity.getSpecialRequest();
        this.orderStatus = orderEntity.getOrderStatus();
        this.client = new UserDTO(orderEntity.getClient());
        this.legalEntity = new LegalEntityDTO(orderEntity.getLegalEntity());
    }

    public OrderDTO(Order orderEntity, Set<Item> items) {
        this(orderEntity);
        items.forEach(item -> this.getItems().add(new ItemDTO(item)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(Instant canceledAt) {
        this.canceledAt = canceledAt;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Duration getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(Duration estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UserDTO getClient() {
        return client;
    }

    public void setClient(UserDTO client) {
        this.client = client;
    }

    public LegalEntityDTO getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(LegalEntityDTO legalEntity) {
        this.legalEntity = legalEntity;
    }

    public List<ItemDTO> getItems() {
        return items;
    }
}

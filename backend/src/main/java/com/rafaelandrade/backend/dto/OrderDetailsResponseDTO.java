package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.Item;
import com.rafaelandrade.backend.entities.Order;
import com.rafaelandrade.backend.entities.common.OrderStatus;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderDetailsResponseDTO {

    private Long id;
    private Instant createdAt;
    private Instant canceledAt;
    private BigDecimal subTotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalPrice;
    private Duration estimatedDeliveryTime;
    private String specialRequest;
    private OrderStatus orderStatus;
    private UserDetailsResponseDTO client;
    private LegalEntityDetailsResponseDTO legalEntity;
    private List<ItemDTO> items = new ArrayList<>();

    public OrderDetailsResponseDTO(){
    }

    public OrderDetailsResponseDTO(Long id, Instant createdAt, Instant canceledAt, BigDecimal subTotal, BigDecimal deliveryFee, BigDecimal totalPrice, Duration estimatedDeliveryTime, String specialRequest, OrderStatus orderStatus, UserDetailsResponseDTO client, LegalEntityDetailsResponseDTO legalEntity) {
        this.id = id;
        this.createdAt = createdAt;
        this.canceledAt = canceledAt;
        this.subTotal = subTotal;
        this.deliveryFee = deliveryFee;
        this.totalPrice = totalPrice;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.specialRequest = specialRequest;
        this.orderStatus = orderStatus;
        this.client = client;
        this.legalEntity = legalEntity;
    }

    public OrderDetailsResponseDTO(Order orderEntity) {
        this.id = orderEntity.getId();
        this.createdAt = orderEntity.getCreatedAt();
        this.canceledAt = orderEntity.getCanceledAt();
        this.subTotal = orderEntity.getSubTotal();
        this.deliveryFee = orderEntity.getDeliveryFee();
        this.totalPrice = orderEntity.getTotalPrice();
        this.estimatedDeliveryTime = orderEntity.getEstimatedDeliveryTime();
        this.specialRequest = orderEntity.getSpecialRequest();
        this.orderStatus = orderEntity.getOrderStatus();
        this.client = new UserDetailsResponseDTO(orderEntity.getClient());
        this.legalEntity = new LegalEntityDetailsResponseDTO(orderEntity.getLegalEntity());
    }

    public OrderDetailsResponseDTO(Order orderEntity, Set<Item> items) {
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

    public UserDetailsResponseDTO getClient() {
        return client;
    }

    public void setClient(UserDetailsResponseDTO client) {
        this.client = client;
    }

    public LegalEntityDetailsResponseDTO getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(LegalEntityDetailsResponseDTO legalEntity) {
        this.legalEntity = legalEntity;
    }

    public List<ItemDTO> getItems() {
        return items;
    }
}

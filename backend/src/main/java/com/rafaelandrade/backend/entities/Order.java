package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.entities.common.OrderStatus;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_order")
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant createdAt;
    private Instant canceledAt;
    private BigDecimal subTotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalPrice;
    private Duration estimatedDeliveryTime;
    private String specialRequest;
    private Integer orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "legal_entity_id")
    private LegalEntity legalEntity;

    @ManyToMany
    @JoinTable(name = "tb_order_item", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> items = new HashSet<>();

    public Order(){
    }

    public Order(Long id, Instant createdAt, Instant canceledAt, BigDecimal subTotal, BigDecimal deliveryFee, BigDecimal totalPrice, Duration estimatedDeliveryTime, String specialRequest, OrderStatus orderStatus, User client, LegalEntity legalEntity) {
        this.id = id;
        this.createdAt = createdAt;
        this.canceledAt = canceledAt;
        this.subTotal = subTotal;
        this.deliveryFee = deliveryFee;
        this.totalPrice = totalPrice;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.specialRequest = specialRequest;
        setOrderStatus(orderStatus);
        this.client = client;
        this.legalEntity = legalEntity;
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
        return OrderStatus.valueOf(orderStatus);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        if (orderStatus != null) {
            this.orderStatus = orderStatus.getCode();
        }
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public LegalEntity getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(LegalEntity legalEntity) {
        this.legalEntity = legalEntity;
    }

    public Set<Item> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

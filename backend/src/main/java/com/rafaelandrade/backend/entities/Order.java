package com.rafaelandrade.backend.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant createdAt;
    private Instant canceledAt;
    private BigDecimal subTotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalPrice;
    private LocalTime estimatedDeliveryTime;
    private String specialRequest;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToMany
    @JoinTable(name = "tb_order_dish", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private Set<Dish> dishes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_order_drink", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "drink_id"))
    private Set<Drink> drinks = new HashSet<>();

    public Order(){
    }

    public Order(Long id, Instant createdAt, Instant canceledAt, BigDecimal subTotal, BigDecimal deliveryFee, BigDecimal totalPrice, LocalTime estimatedDeliveryTime, String specialRequest, User client, Restaurant restaurant) {
        this.id = id;
        this.createdAt = createdAt;
        this.canceledAt = canceledAt;
        this.subTotal = subTotal;
        this.deliveryFee = deliveryFee;
        this.totalPrice = totalPrice;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.specialRequest = specialRequest;
        this.client = client;
        this.restaurant = restaurant;
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

    public LocalTime getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(LocalTime estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public Set<Drink> getDrinks() {
        return drinks;
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

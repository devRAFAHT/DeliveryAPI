package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.common.OrderStatus;
import com.rafaelandrade.backend.entities.Dish;
import com.rafaelandrade.backend.entities.Drink;
import com.rafaelandrade.backend.entities.Order;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
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
    private RestaurantDTO restaurant;
    private List<DishDTO> dishes = new ArrayList<>();
    private List<DrinkDTO> drinks = new ArrayList<>();

    public OrderDTO(){
    }

    public OrderDTO(Long id, BigDecimal deliveryFee, String specialRequest, UserDTO client, RestaurantDTO restaurant) {
        this.id = id;
        this.deliveryFee = deliveryFee;
        this.specialRequest = specialRequest;
        this.client = client;
    }

    public OrderDTO(Order orderEntity){
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
        this.restaurant = new RestaurantDTO(orderEntity.getRestaurant());
    }

    public OrderDTO(Order orderEntity, Set<Dish> dishes, Set<Drink> drinks){
        this(orderEntity);
        dishes.forEach(dish -> this.getDishes().add(new DishDTO(dish)));
        drinks.forEach(drink -> this.getDrinks().add(new DrinkDTO(drink)));
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

    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }

    public List<DishDTO> getDishes() {
        return dishes;
    }

    public List<DrinkDTO> getDrinks() {
        return drinks;
    }
}

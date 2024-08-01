package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.Assessment;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssessmentDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String comment;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer points;
    private UserDTO user;
    private RestaurantDTO restaurant;
    private DishDTO dish;
    private DrinkDTO drink;
    private Map<Instant, String> updateHistory = new HashMap<>();

    public AssessmentDTO(){
    }

    public AssessmentDTO(Long id, String comment, Integer points, UserDTO user, DishDTO dish, DrinkDTO drink) {
        this.id = id;
        this.comment = comment;
        this.points = points;
        this.user = user;
        this.dish = dish;
        this.drink = drink;
    }

    public AssessmentDTO(Assessment assessmentEntity) {
        this.id = assessmentEntity.getId();
        this.comment = assessmentEntity.getComment();
        this.createdAt = assessmentEntity.getCreatedAt();
        this.updatedAt = assessmentEntity.getUpdatedAt();
        this.points = assessmentEntity.getPoints();
        this.user = new UserDTO(assessmentEntity.getUser());
        this.restaurant = new RestaurantDTO(assessmentEntity.getRestaurant());
        this.updateHistory = (assessmentEntity.getUpdateHistory() != null) ? new HashMap<>(assessmentEntity.getUpdateHistory()) : new HashMap<>();
        this.dish = (assessmentEntity.getDish() != null) ? new DishDTO(assessmentEntity.getDish()) : null;
        this.drink = (assessmentEntity.getDrink() != null) ? new DrinkDTO(assessmentEntity.getDrink()) : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }

    public DishDTO getDish() {
        return dish;
    }

    public void setDish(DishDTO dish) {
        this.dish = dish;
    }

    public DrinkDTO getDrink() {
        return drink;
    }

    public void setDrink(DrinkDTO drink) {
        this.drink = drink;
    }

    public Map<Instant, String> getUpdateHistory() {
        return updateHistory;
    }
}

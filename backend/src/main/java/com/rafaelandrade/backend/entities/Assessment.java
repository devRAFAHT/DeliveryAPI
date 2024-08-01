package com.rafaelandrade.backend.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "tb_assessment")
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer points;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne()
    @JoinColumn(name = "dish_id", nullable = true)
    private Dish dish;

    @ManyToOne()
    @JoinColumn(name = "drink_id", nullable = true)
    private Drink drink;

    @ElementCollection
    @CollectionTable(name = "assessment_update_history", joinColumns = @JoinColumn(name = "assessment_id"))
    @MapKeyColumn(name = "update_date")
    @Column(name = "comment")
    private Map<Instant, String> updateHistory = new HashMap<>();

    @OneToMany(mappedBy = "assessment")
    private Set<AssessmentResponse> assessmentResponses = new HashSet<>();

    public Assessment(){
    }

    public Assessment(Long id, String comment, Instant createdAt, Instant updatedAt, Integer points, User user, Restaurant restaurant, Dish dish, Drink drink) {
        this.id = id;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.points = points;
        this.user = user;
        this.restaurant = restaurant;
        this.dish = dish;
        this.drink = drink;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public Map<Instant, String> getUpdateHistory() {
        return updateHistory;
    }

    public void setUpdateHistory(Map<Instant, String> updateHistory) {
        this.updateHistory = updateHistory;
    }

    public Set<AssessmentResponse> getAssessmentResponses() {
        return assessmentResponses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assessment that = (Assessment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

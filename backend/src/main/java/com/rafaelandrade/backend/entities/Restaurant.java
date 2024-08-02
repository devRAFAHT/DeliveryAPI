package com.rafaelandrade.backend.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_restaurant")
public class Restaurant extends LegalEntity{

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Menu> menus = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_association_restaurant_category", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<RestaurantCategory> categories = new HashSet<>();

    @ManyToMany(mappedBy = "favoritesRestaurants")
    private Set<User> favoriteBy = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    private Set<Order> orderHistory = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    private Set<Assessment> assessments = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    private Set<AssessmentResponse> assessmentResponses = new HashSet<>();

    /*
    private Double averageRating;
    private Integer numberOfReviews;
     */

    public Restaurant(){
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public Set<RestaurantCategory> getCategories() {
        return categories;
    }

    public Set<User> getFavoriteBy() {
        return favoriteBy;
    }

    public Set<Order> getOrderHistory() {
        return orderHistory;
    }

    public Set<Assessment> getAssessments() {
        return assessments;
    }

    public Set<AssessmentResponse> getAssessmentResponses() {
        return assessmentResponses;
    }
}

package com.rafaelandrade.backend.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_user")
public class User extends PhysicalPerson{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bag_id")
    private Bag bag;

    @ManyToMany
    @JoinTable(name = "tb_user_favorite_restaurant", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
    private Set<Restaurant> favoritesRestaurants = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_user_favorite_dish", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private Set<Dish> favoritesDishes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_user_favorite_drink", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "drink_id"))
    private Set<Drink> favoritesDrinks = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private Set<Order> orderHitory = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Assessment> assessments = new HashSet<>();

    /* paymentMethods
     */

    public User(){

    }

    public User(Bag bag) {
        this.bag = bag;
    }

    public User(String firstName, String lastName, String username, String gender, String personalDocument, String dateOfBirth, Bag bag) {
        super(firstName, lastName, username, gender, personalDocument, dateOfBirth);
        this.bag = bag;
    }

    public User(Long id, String email, String password, String dateOfBirth, String phoneNumber, String profilePictureUrl, String biography, Instant createdAt, Instant updatedAt, Boolean active, String firstName, String lastName, String username, String gender, String personalDocument, String dateOfBirth1, Bag bag) {
        super(id, email, password, dateOfBirth, phoneNumber, profilePictureUrl, biography, createdAt, updatedAt, active, firstName, lastName, username, gender, personalDocument, dateOfBirth1);
        this.bag = bag;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public Set<Restaurant> getFavoritesRestaurants() {
        return favoritesRestaurants;
    }

    public Set<Dish> getFavoritesDishes() {
        return favoritesDishes;
    }

    public Set<Drink> getFavoritesDrinks() {
        return favoritesDrinks;
    }

    public Set<Order> getOrderHitory() {
        return orderHitory;
    }

    public Set<Assessment> getAssessments() {
        return assessments;
    }
}

package com.rafaelandrade.backend.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_bag")
public class Bag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "bag")
    private User user;
    private Integer quantityOfItems;
    private BigDecimal totalPrice;
    @Column(precision = 5, scale = 2)
    private BigDecimal discount;

    @ManyToMany
    @JoinTable(name = "tb_bag_dish", joinColumns = @JoinColumn(name = "bag_id"), inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private Set<Dish> dishes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_bag_drink", joinColumns = @JoinColumn(name = "bag_id"), inverseJoinColumns = @JoinColumn(name = "drink_id"))
    private Set<Drink> drinks = new HashSet<>();

    public Bag(){
    }

    public Bag(Long id, User user, Integer quantityOfItems, BigDecimal totalPrice, BigDecimal discount) {
        this.id = id;
        this.user = user;
        this.quantityOfItems = quantityOfItems;
        this.totalPrice = totalPrice;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getQuantityOfItems() {
        return quantityOfItems;
    }

    public void setQuantityOfItems(Integer quantityOfItems) {
        this.quantityOfItems = quantityOfItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
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
        Bag bag = (Bag) o;
        return Objects.equals(id, bag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

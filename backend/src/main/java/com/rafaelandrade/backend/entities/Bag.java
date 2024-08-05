package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.dto.BagDTO;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_bag")
public class Bag implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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
    @JoinTable(name = "tb_bag_item", joinColumns = @JoinColumn(name = "bag_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> items = new HashSet<>();

    public Bag(){
    }

    public Bag(Long id,Integer quantityOfItems, BigDecimal totalPrice, BigDecimal discount) {
        this.id = id;
        this.quantityOfItems = quantityOfItems;
        this.totalPrice = totalPrice;
        this.discount = discount;
    }

    public Bag(Integer quantityOfItems, BigDecimal totalPrice, BigDecimal discount) {
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

    public Set<Item> getItems() {
        return items;
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

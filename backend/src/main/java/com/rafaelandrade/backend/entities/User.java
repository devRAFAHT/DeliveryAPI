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
    @JoinTable(name = "tb_user_favorite_legal_entity", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "legal_entity_id"))
    private Set<LegalEntity> favoriteEstablishments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_user_favorite_item", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> favoritesItems = new HashSet<>();

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

    public Set<LegalEntity> getFavoriteEstablishments() {
        return favoriteEstablishments;
    }

    public Set<Item> getFavoritesItems() {
        return favoritesItems;
    }

    public Set<Order> getOrderHitory() {
        return orderHitory;
    }

    public Set<Assessment> getAssessments() {
        return assessments;
    }
}

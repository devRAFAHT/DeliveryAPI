package com.rafaelandrade.backend.entities;

import com.rafaelandrade.backend.dto.UserDTO;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_user")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String personalDocument;
    private String dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String profilePictureUrl;
    private String biography;
    private Instant createdAt;
    private Instant updatedAt;
    private Boolean active;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bag_id")
    private Bag bag;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Address> addresses = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_user_favorite_restaurant", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
    private Set<Restaurant> favoritesRestaurants = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_user_favorite_dish", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private Set<Dish> favoritesDishes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_user_favorite_drink", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "drink_id"))
    private Set<Drink> favoritesDrinks = new HashSet<>();

    /* private Set<Order> orderHistory;
    Assessments
    paymentMethods
     */

    public User(){
    }

    public User(Long id, String userName, String firstName, String lastName, String email, String password, String personalDocument, String dateOfBirth, String gender, String phoneNumber, String profilePictureUrl, String biography, Instant createdAt, Instant updatedAt, Boolean active, Bag bag) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.personalDocument = personalDocument;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.profilePictureUrl = profilePictureUrl;
        this.biography = biography;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
        this.bag = bag;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPersonalDocument() {
        return personalDocument;
    }

    public void setPersonalDocument(String personalDocument) {
        this.personalDocument = personalDocument;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Set<Address> getAddresses() {
        return addresses;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName);
    }
}

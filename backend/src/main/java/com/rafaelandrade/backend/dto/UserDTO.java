package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.User;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String personalDocument;
    private String dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String profilePictureUrl;
    private String biography;
    private Instant createdAt;
    private Instant updatedAt;
    private Boolean active;
    private Set<RoleDTO> roles = new HashSet<>();
    private List<AddressDTO> address = new ArrayList<>();
    private List<RestaurantDTO> favoritesRestaurants = new ArrayList<>();
    private List<DishDTO> favoritesDishes = new ArrayList<>();
    private List<DrinkDTO> favoritesDrinks = new ArrayList<>();

    public UserDTO(){
    }

    public UserDTO(Long id, String userName, String firstName, String lastName, String email, String personalDocument, String dateOfBirth, String gender, String phoneNumber, String profilePictureUrl, String biography, Instant createdAt, Instant updatedAt, Boolean active) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.personalDocument = personalDocument;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.profilePictureUrl = profilePictureUrl;
        this.biography = biography;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
    }

    public UserDTO(User userEntity) {
        this.id = userEntity.getId();
        this.userName = userEntity.getUserName();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.email = userEntity.getEmail();
        this.personalDocument = userEntity.getPersonalDocument();
        this.dateOfBirth = userEntity.getDateOfBirth();
        this.gender = userEntity.getGender();
        this.phoneNumber = userEntity.getPhoneNumber();
        this.profilePictureUrl = userEntity.getProfilePictureUrl();
        this.biography = userEntity.getBiography();
        this.createdAt = userEntity.getCreatedAt();
        this.updatedAt = userEntity.getUpdatedAt();
        this.active = userEntity.getActive();
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

    public String getPersonalDocument() {
        return personalDocument;
    }

    public void setPersonalDocument(String personalDocument) {
        this.personalDocument = personalDocument;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public List<AddressDTO> getAddress() {
        return address;
    }

    public List<RestaurantDTO> getFavoritesRestaurants() {
        return favoritesRestaurants;
    }

    public List<DishDTO> getFavoritesDishes() {
        return favoritesDishes;
    }

    public List<DrinkDTO> getFavoritesDrinks() {
        return favoritesDrinks;
    }
}

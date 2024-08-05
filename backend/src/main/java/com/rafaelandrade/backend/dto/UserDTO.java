package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.*;

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
    private String username;
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
    private BagDTO bag;
    private Set<RoleDTO> roles = new HashSet<>();
    private List<AddressDTO> addresses = new ArrayList<>();
    private List<LegalEntityDTO> favoriteEstablishments = new ArrayList<>();
    private List<ItemDTO> favoritesItems = new ArrayList<>();

    public UserDTO(){
    }

    public UserDTO(Long id, String username, String firstName, String lastName, String email, String personalDocument, String dateOfBirth, String gender, String phoneNumber, String profilePictureUrl, String biography, Instant createdAt, Instant updatedAt, Boolean active, BagDTO bag) {
        this.id = id;
        this.username = username;
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
        this.bag = bag;
    }

    public UserDTO(User userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
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
        this.bag = new BagDTO(userEntity.getBag());
        userEntity.getRoles().forEach(role -> this.getRoles().add(new RoleDTO(role)));
    }

    public UserDTO(User userEntity, Set<Address> addresses, Set<LegalEntity> favoriteEstablishments, Set<Item> favoritesItems) {
        this(userEntity);
        addresses.forEach(address -> this.getAddresses().add(new AddressDTO(address)));
        favoritesItems.forEach(item -> this.getFavoritesItems().add(new ItemDTO(item)));
        favoriteEstablishments.forEach(legalEntity -> this.getFavoriteEstablishments().add(new LegalEntityDTO(legalEntity)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public BagDTO getBag() {
        return bag;
    }

    public void setBag(BagDTO bag) {
        this.bag = bag;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public List<LegalEntityDTO> getFavoriteEstablishments() {
        return favoriteEstablishments;
    }

    public List<ItemDTO> getFavoritesItems() {
        return favoritesItems;
    }
}

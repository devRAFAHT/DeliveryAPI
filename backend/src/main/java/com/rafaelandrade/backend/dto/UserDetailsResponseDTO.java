package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.User;

public class UserDetailsResponseDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePictureUrl;

    public UserDetailsResponseDTO(){
    }

    public UserDetailsResponseDTO(User userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.email = userEntity.getEmail();
        this.profilePictureUrl = userEntity.getProfilePictureUrl();
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

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}

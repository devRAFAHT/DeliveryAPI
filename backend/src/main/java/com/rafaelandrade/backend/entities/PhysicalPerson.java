package com.rafaelandrade.backend.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;

import java.io.Serial;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@MappedSuperclass
public abstract class PhysicalPerson extends Person{
    @Serial
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private String username;
    private String gender;
    private String personalDocument;
    private String dateOfBirth;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "physical_person_id")
    private Set<Address> addresses = new HashSet<>();

    public PhysicalPerson(){
    }

    public PhysicalPerson(String firstName, String lastName, String username, String gender, String personalDocument, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.gender = gender;
        this.personalDocument = personalDocument;
        this.dateOfBirth = dateOfBirth;
    }

    public PhysicalPerson(Long id, String email, String password, String dateOfBirth, String phoneNumber, String profilePictureUrl, String biography, Instant createdAt, Instant updatedAt, Boolean active, String firstName, String lastName, String username, String gender, String personalDocument, String dateOfBirth1) {
        super(id, email, password, dateOfBirth, phoneNumber, profilePictureUrl, biography, createdAt, updatedAt, active);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.gender = gender;
        this.personalDocument = personalDocument;
        this.dateOfBirth = dateOfBirth1;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonalDocument() {
        return personalDocument;
    }

    public void setPersonalDocument(String personalDocument) {
        this.personalDocument = personalDocument;
    }

    @Override
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhysicalPerson that = (PhysicalPerson) o;
        return Objects.equals(personalDocument, that.personalDocument);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(personalDocument);
    }
}

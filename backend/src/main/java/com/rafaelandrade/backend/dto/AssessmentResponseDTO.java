package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.AssessmentResponse;

import java.time.Instant;

public class AssessmentResponseDTO {

    private Long id;
    private String comment;
    private Instant createdAt;
    private RestaurantDTO restaurant;
    private AssessmentDTO assessment;

    public AssessmentResponseDTO(){
    }

    public AssessmentResponseDTO(Long id, String comment, RestaurantDTO restaurant, AssessmentDTO assessment) {
        this.id = id;
        this.comment = comment;
        this.restaurant = restaurant;
        this.assessment = assessment;
    }

    public AssessmentResponseDTO(AssessmentResponse assessmentResponseEntity) {
        this.id = assessmentResponseEntity.getId();
        this.comment = assessmentResponseEntity.getComment();
        this.createdAt = assessmentResponseEntity.getCreatedAt();
        this.restaurant = new RestaurantDTO(assessmentResponseEntity.getRestaurant());
        this.assessment = new AssessmentDTO(assessmentResponseEntity.getAssessment());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }

    public AssessmentDTO getAssessment() {
        return assessment;
    }

    public void setAssessment(AssessmentDTO assessment) {
        this.assessment = assessment;
    }
}

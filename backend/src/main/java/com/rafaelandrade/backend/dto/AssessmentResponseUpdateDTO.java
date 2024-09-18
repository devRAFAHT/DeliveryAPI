package com.rafaelandrade.backend.dto;

public class AssessmentResponseUpdateDTO {

    private String comment;

    public AssessmentResponseUpdateDTO(){
    }

    public AssessmentResponseUpdateDTO(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

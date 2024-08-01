package com.rafaelandrade.backend.dto;

import java.io.Serial;
import java.io.Serializable;

public class AssessmentUpdateDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String comment;

    public AssessmentUpdateDTO(){
    }

    public AssessmentUpdateDTO(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

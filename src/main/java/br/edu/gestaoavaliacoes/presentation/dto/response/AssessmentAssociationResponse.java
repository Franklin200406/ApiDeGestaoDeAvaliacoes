package br.edu.gestaoavaliacoes.presentation.dto.response;

import java.math.BigDecimal;

public class AssessmentAssociationResponse {

    private Long questionId;

    private BigDecimal value;

    private String description;

    public AssessmentAssociationResponse() {
    }

    public AssessmentAssociationResponse(Long questionId, BigDecimal value, String description) {
        this.questionId = questionId;
        this.value = value;
        this.description = description;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
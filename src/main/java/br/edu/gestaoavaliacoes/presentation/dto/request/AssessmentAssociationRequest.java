package br.edu.gestaoavaliacoes.presentation.dto.request;

import java.math.BigDecimal;

public class AssessmentAssociationRequest {

    private Long questionId;

    private BigDecimal value;

    public AssessmentAssociationRequest() {
    }

    public AssessmentAssociationRequest(Long questionId, BigDecimal value) {
        this.questionId = questionId;
        this.value = value;
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
}
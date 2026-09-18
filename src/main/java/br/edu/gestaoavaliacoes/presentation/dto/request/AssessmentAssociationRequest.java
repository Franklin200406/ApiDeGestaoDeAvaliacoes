package br.edu.gestaoavaliacoes.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class AssessmentAssociationRequest {

    @NotNull(message = "Questão é obrigatória")
    private Long questionId;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser maior que zero")
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
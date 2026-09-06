package br.edu.gestaoavaliacoes.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AssessmentQuestionKey implements Serializable {

    private Long assessmentId;

    private Long questionId;

    public AssessmentQuestionKey() {
    }

    public AssessmentQuestionKey(Long assessmentId, Long questionId) {
        this.assessmentId = assessmentId;
        this.questionId = questionId;
    }

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssessmentQuestionKey that = (AssessmentQuestionKey) o;
        return Objects.equals(assessmentId, that.assessmentId)
                && Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assessmentId, questionId);
    }
}
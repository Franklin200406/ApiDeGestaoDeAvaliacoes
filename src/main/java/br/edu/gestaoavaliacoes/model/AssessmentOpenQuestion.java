package br.edu.gestaoavaliacoes.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "assessment_open_questions")
public class AssessmentOpenQuestion {

    @EmbeddedId
    private AssessmentQuestionKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("assessmentId")
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("questionId")
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal value;

    public AssessmentOpenQuestion() {
    }

    public AssessmentOpenQuestion(Assessment assessment, Question question, BigDecimal value) {
        this.assessment = assessment;
        this.question = question;
        this.value = value;
    }

    public AssessmentQuestionKey getId() {
        return id;
    }

    public void setId(AssessmentQuestionKey id) {
        this.id = id;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
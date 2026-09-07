package br.edu.gestaoavaliacoes.presentation.dto.request;

import br.edu.gestaoavaliacoes.model.enums.AssessmentType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AssessmentRequest {

    private Long courseId;

    private Long disciplineId;

    private String semester;

    private String teacher;

    private LocalDate assessmentDate;

    private LocalDate elaborationDate;

    private AssessmentType type;

    private BigDecimal value;

    private Integer multipleChoiceQuestionCount;

    private Integer openQuestionCount;

    public AssessmentRequest() {
    }

    public AssessmentRequest(Long courseId, Long disciplineId, String semester, String teacher,
                             LocalDate assessmentDate, LocalDate elaborationDate, AssessmentType type,
                             BigDecimal value, Integer multipleChoiceQuestionCount, Integer openQuestionCount) {
        this.courseId = courseId;
        this.disciplineId = disciplineId;
        this.semester = semester;
        this.teacher = teacher;
        this.assessmentDate = assessmentDate;
        this.elaborationDate = elaborationDate;
        this.type = type;
        this.value = value;
        this.multipleChoiceQuestionCount = multipleChoiceQuestionCount;
        this.openQuestionCount = openQuestionCount;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Long disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(LocalDate assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public LocalDate getElaborationDate() {
        return elaborationDate;
    }

    public void setElaborationDate(LocalDate elaborationDate) {
        this.elaborationDate = elaborationDate;
    }

    public AssessmentType getType() {
        return type;
    }

    public void setType(AssessmentType type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Integer getMultipleChoiceQuestionCount() {
        return multipleChoiceQuestionCount;
    }

    public void setMultipleChoiceQuestionCount(Integer multipleChoiceQuestionCount) {
        this.multipleChoiceQuestionCount = multipleChoiceQuestionCount;
    }

    public Integer getOpenQuestionCount() {
        return openQuestionCount;
    }

    public void setOpenQuestionCount(Integer openQuestionCount) {
        this.openQuestionCount = openQuestionCount;
    }
}
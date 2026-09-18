package br.edu.gestaoavaliacoes.presentation.dto.request;

import br.edu.gestaoavaliacoes.model.enums.AssessmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AssessmentRequest {

    @NotNull(message = "Curso é obrigatório")
    private Long courseId;

    @NotNull(message = "Disciplina é obrigatória")
    private Long disciplineId;

    @NotBlank(message = "Semestre é obrigatório")
    private String semester;

    @NotBlank(message = "Docente é obrigatório")
    private String teacher;

    @NotNull(message = "Data da avaliação é obrigatória")
    private LocalDate assessmentDate;

    @NotNull(message = "Data de elaboração é obrigatória")
    private LocalDate elaborationDate;

    @NotNull(message = "Tipo da avaliação é obrigatório")
    private AssessmentType type;

    @NotNull(message = "Valor da avaliação é obrigatório")
    @Positive(message = "Valor da avaliação deve ser maior que zero")
    private BigDecimal value;

    @NotNull(message = "Quantidade de questões de múltipla escolha é obrigatória")
    @PositiveOrZero(message = "Quantidade de questões de múltipla escolha não pode ser negativa")
    private Integer multipleChoiceQuestionCount;

    @NotNull(message = "Quantidade de questões abertas é obrigatória")
    @PositiveOrZero(message = "Quantidade de questões abertas não pode ser negativa")
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
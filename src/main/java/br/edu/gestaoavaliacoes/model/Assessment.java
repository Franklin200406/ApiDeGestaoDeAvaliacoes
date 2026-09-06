package br.edu.gestaoavaliacoes.model;

import br.edu.gestaoavaliacoes.model.enums.AssessmentType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assessments")
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String semester;

    @Column(nullable = false, length = 150)
    private String teacher;

    @Column(name = "assessment_date", nullable = false)
    private LocalDate assessmentDate;

    @Column(name = "elaboration_date", nullable = false)
    private LocalDate elaborationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private AssessmentType type;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal value;

    @Column(name = "multiple_choice_question_count", nullable = false)
    private Integer multipleChoiceQuestionCount;

    @Column(name = "open_question_count", nullable = false)
    private Integer openQuestionCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "assessment")
    private List<AssessmentMultipleChoiceQuestion> multipleChoiceQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "assessment")
    private List<AssessmentOpenQuestion> openQuestions = new ArrayList<>();

    public Assessment() {
    }

    public Assessment(String semester, String teacher, LocalDate assessmentDate, LocalDate elaborationDate,
                      AssessmentType type, BigDecimal value, Integer multipleChoiceQuestionCount,
                      Integer openQuestionCount, Course course, Discipline discipline, User author) {
        this.semester = semester;
        this.teacher = teacher;
        this.assessmentDate = assessmentDate;
        this.elaborationDate = elaborationDate;
        this.type = type;
        this.value = value;
        this.multipleChoiceQuestionCount = multipleChoiceQuestionCount;
        this.openQuestionCount = openQuestionCount;
        this.course = course;
        this.discipline = discipline;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<AssessmentMultipleChoiceQuestion> getMultipleChoiceQuestions() {
        return multipleChoiceQuestions;
    }

    public void setMultipleChoiceQuestions(List<AssessmentMultipleChoiceQuestion> multipleChoiceQuestions) {
        this.multipleChoiceQuestions = multipleChoiceQuestions;
    }

    public List<AssessmentOpenQuestion> getOpenQuestions() {
        return openQuestions;
    }

    public void setOpenQuestions(List<AssessmentOpenQuestion> openQuestions) {
        this.openQuestions = openQuestions;
    }
}
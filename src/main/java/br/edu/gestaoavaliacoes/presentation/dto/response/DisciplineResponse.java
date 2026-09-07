package br.edu.gestaoavaliacoes.presentation.dto.response;

import br.edu.gestaoavaliacoes.model.enums.Shift;

public class DisciplineResponse {

    private Long id;

    private String name;

    private String description;

    private Integer workloadHours;

    private Shift shift;

    private Long courseId;

    public DisciplineResponse() {
    }

    public DisciplineResponse(Long id, String name, String description, Integer workloadHours,
                              Shift shift, Long courseId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.workloadHours = workloadHours;
        this.shift = shift;
        this.courseId = courseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWorkloadHours() {
        return workloadHours;
    }

    public void setWorkloadHours(Integer workloadHours) {
        this.workloadHours = workloadHours;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
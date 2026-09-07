package br.edu.gestaoavaliacoes.presentation.dto.response;

import br.edu.gestaoavaliacoes.model.enums.ClassFormat;

public class CourseResponse {

    private Long id;

    private String name;

    private String description;

    private ClassFormat classFormat;

    public CourseResponse() {
    }

    public CourseResponse(Long id, String name, String description, ClassFormat classFormat) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.classFormat = classFormat;
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

    public ClassFormat getClassFormat() {
        return classFormat;
    }

    public void setClassFormat(ClassFormat classFormat) {
        this.classFormat = classFormat;
    }
}
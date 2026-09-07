package br.edu.gestaoavaliacoes.presentation.dto.request;

import br.edu.gestaoavaliacoes.model.enums.ClassFormat;

public class CourseRequest {

    private String name;

    private String description;

    private ClassFormat classFormat;

    public CourseRequest() {
    }

    public CourseRequest(String name, String description, ClassFormat classFormat) {
        this.name = name;
        this.description = description;
        this.classFormat = classFormat;
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
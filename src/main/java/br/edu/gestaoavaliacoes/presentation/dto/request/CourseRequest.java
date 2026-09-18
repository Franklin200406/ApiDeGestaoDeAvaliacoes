package br.edu.gestaoavaliacoes.presentation.dto.request;

import br.edu.gestaoavaliacoes.model.enums.ClassFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CourseRequest {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    private String description;

    @NotNull(message = "Formato de aula é obrigatório")
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
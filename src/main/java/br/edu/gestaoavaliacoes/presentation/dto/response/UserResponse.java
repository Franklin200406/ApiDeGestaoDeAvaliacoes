package br.edu.gestaoavaliacoes.presentation.dto.response;

import br.edu.gestaoavaliacoes.model.enums.UserType;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {

    private Long id;

    private String email;

    private String name;

    private UserType type;

    private List<Long> courseIds = new ArrayList<>();

    private List<Long> disciplineIds = new ArrayList<>();

    public UserResponse() {
    }

    public UserResponse(Long id, String email, String name, UserType type,
                        List<Long> courseIds, List<Long> disciplineIds) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.type = type;
        this.courseIds = courseIds;
        this.disciplineIds = disciplineIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public List<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<Long> courseIds) {
        this.courseIds = courseIds;
    }

    public List<Long> getDisciplineIds() {
        return disciplineIds;
    }

    public void setDisciplineIds(List<Long> disciplineIds) {
        this.disciplineIds = disciplineIds;
    }
}
package br.edu.gestaoavaliacoes.presentation.dto.request;

public class UserDisciplineRequest {

    private Long disciplineId;

    public UserDisciplineRequest() {
    }

    public UserDisciplineRequest(Long disciplineId) {
        this.disciplineId = disciplineId;
    }

    public Long getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Long disciplineId) {
        this.disciplineId = disciplineId;
    }
}
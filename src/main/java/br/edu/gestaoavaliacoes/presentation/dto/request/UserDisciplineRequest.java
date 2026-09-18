package br.edu.gestaoavaliacoes.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public class UserDisciplineRequest {

    @NotNull(message = "Disciplina é obrigatória")
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
package br.edu.gestaoavaliacoes.presentation.dto.request;

import br.edu.gestaoavaliacoes.model.enums.Difficulty;
import br.edu.gestaoavaliacoes.model.enums.QuestionType;

import java.util.ArrayList;
import java.util.List;

public class QuestionRequest {

    private QuestionType type;

    private Difficulty difficulty;

    private String description;

    private Long disciplineId;

    private Integer optionCount;

    private List<OptionRequest> options = new ArrayList<>();

    public QuestionRequest() {
    }

    public QuestionRequest(QuestionType type, Difficulty difficulty, String description, Long disciplineId,
                           Integer optionCount, List<OptionRequest> options) {
        this.type = type;
        this.difficulty = difficulty;
        this.description = description;
        this.disciplineId = disciplineId;
        this.optionCount = optionCount;
        this.options = options;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Long disciplineId) {
        this.disciplineId = disciplineId;
    }

    public Integer getOptionCount() {
        return optionCount;
    }

    public void setOptionCount(Integer optionCount) {
        this.optionCount = optionCount;
    }

    public List<OptionRequest> getOptions() {
        return options;
    }

    public void setOptions(List<OptionRequest> options) {
        this.options = options;
    }
}
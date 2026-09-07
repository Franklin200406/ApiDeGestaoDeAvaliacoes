package br.edu.gestaoavaliacoes.presentation.dto.response;

import br.edu.gestaoavaliacoes.model.enums.Difficulty;
import br.edu.gestaoavaliacoes.model.enums.QuestionType;

import java.util.ArrayList;
import java.util.List;

public class QuestionResponse {

    private Long id;

    private QuestionType type;

    private Difficulty difficulty;

    private String description;

    private Long disciplineId;

    private Integer optionCount;

    private List<OptionResponse> options = new ArrayList<>();

    private Long correctOptionId;

    public QuestionResponse() {
    }

    public QuestionResponse(Long id, QuestionType type, Difficulty difficulty, String description,
                            Long disciplineId, Integer optionCount, List<OptionResponse> options,
                            Long correctOptionId) {
        this.id = id;
        this.type = type;
        this.difficulty = difficulty;
        this.description = description;
        this.disciplineId = disciplineId;
        this.optionCount = optionCount;
        this.options = options;
        this.correctOptionId = correctOptionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<OptionResponse> getOptions() {
        return options;
    }

    public void setOptions(List<OptionResponse> options) {
        this.options = options;
    }

    public Long getCorrectOptionId() {
        return correctOptionId;
    }

    public void setCorrectOptionId(Long correctOptionId) {
        this.correctOptionId = correctOptionId;
    }
}
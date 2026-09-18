package br.edu.gestaoavaliacoes.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OptionRequest {

    @NotBlank(message = "Texto da opção é obrigatório")
    private String text;

    @NotNull(message = "É necessário informar se a opção é a correta")
    private Boolean correct;

    public OptionRequest() {
    }

    public OptionRequest(String text, Boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}
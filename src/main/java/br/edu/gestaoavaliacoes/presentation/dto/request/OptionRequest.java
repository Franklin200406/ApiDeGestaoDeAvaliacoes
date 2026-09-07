package br.edu.gestaoavaliacoes.presentation.dto.request;

public class OptionRequest {

    private String text;

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
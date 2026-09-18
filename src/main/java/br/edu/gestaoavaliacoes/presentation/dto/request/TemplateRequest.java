package br.edu.gestaoavaliacoes.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public class TemplateRequest {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "Nome do arquivo é obrigatório")
    private String fileName;

    @NotBlank(message = "Conteúdo HTML do template é obrigatório")
    private String content;

    public TemplateRequest() {
    }

    public TemplateRequest(String name, String fileName, String content) {
        this.name = name;
        this.fileName = fileName;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
package br.edu.gestaoavaliacoes.presentation.dto.request;

public class TemplateRequest {

    private String name;

    private String fileName;

    public TemplateRequest() {
    }

    public TemplateRequest(String name, String fileName) {
        this.name = name;
        this.fileName = fileName;
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
}
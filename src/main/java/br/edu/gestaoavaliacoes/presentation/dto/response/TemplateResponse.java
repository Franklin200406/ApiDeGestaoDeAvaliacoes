package br.edu.gestaoavaliacoes.presentation.dto.response;

public class TemplateResponse {

    private Long id;

    private String name;

    private String fileName;

    public TemplateResponse() {
    }

    public TemplateResponse(Long id, String name, String fileName) {
        this.id = id;
        this.name = name;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
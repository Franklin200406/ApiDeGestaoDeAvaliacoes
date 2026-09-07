package br.edu.gestaoavaliacoes.presentation.dto.request;

public class UserUpdateRequest {

    private String email;

    private String name;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(String email, String name) {
        this.email = email;
        this.name = name;
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
}
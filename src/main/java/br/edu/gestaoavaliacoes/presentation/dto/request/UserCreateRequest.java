package br.edu.gestaoavaliacoes.presentation.dto.request;

import br.edu.gestaoavaliacoes.model.enums.UserType;

public class UserCreateRequest {

    private String email;

    private String name;

    private UserType type;

    private String password;

    public UserCreateRequest() {
    }

    public UserCreateRequest(String email, String name, UserType type, String password) {
        this.email = email;
        this.name = name;
        this.type = type;
        this.password = password;
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

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
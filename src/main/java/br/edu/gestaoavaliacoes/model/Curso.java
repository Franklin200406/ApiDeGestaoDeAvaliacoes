package br.edu.gestaoavaliacoes.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "formato_aula", nullable = false, length = 20)
    private String formatoAula;

    public Curso() {
    }

    public Curso(String nome, String descricao, String formatoAula) {
        this.nome = nome;
        this.descricao = descricao;
        this.formatoAula = formatoAula;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFormatoAula() {
        return formatoAula;
    }

    public void setFormatoAula(String formatoAula) {
        this.formatoAula = formatoAula;
    }
}
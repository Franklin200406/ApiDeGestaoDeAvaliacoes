package br.edu.gestaoavaliacoes.model;

import br.edu.gestaoavaliacoes.model.enums.ClassFormat;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "class_format", nullable = false, length = 20)
    private ClassFormat classFormat;

    @OneToMany(mappedBy = "course")
    private Set<Discipline> disciplines = new HashSet<>();

    @ManyToMany(mappedBy = "courses")
    private Set<User> users = new HashSet<>();

    public Course() {
    }

    public Course(String name, String description, ClassFormat classFormat) {
        this.name = name;
        this.description = description;
        this.classFormat = classFormat;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClassFormat getClassFormat() {
        return classFormat;
    }

    public void setClassFormat(ClassFormat classFormat) {
        this.classFormat = classFormat;
    }

    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
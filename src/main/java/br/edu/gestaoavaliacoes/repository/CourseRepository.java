package br.edu.gestaoavaliacoes.repository;

import br.edu.gestaoavaliacoes.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
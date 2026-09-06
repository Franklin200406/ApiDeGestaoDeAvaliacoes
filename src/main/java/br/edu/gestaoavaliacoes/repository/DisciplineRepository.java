package br.edu.gestaoavaliacoes.repository;

import br.edu.gestaoavaliacoes.model.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

    List<Discipline> findByCourseId(Long courseId);
}
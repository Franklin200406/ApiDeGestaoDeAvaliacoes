package br.edu.gestaoavaliacoes.repository;

import br.edu.gestaoavaliacoes.model.Discipline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

    Page<Discipline> findByCourseId(Long courseId, Pageable pageable);

    boolean existsByCourseId(Long courseId);
}
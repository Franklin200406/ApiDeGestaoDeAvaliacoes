package br.edu.gestaoavaliacoes.repository;

import br.edu.gestaoavaliacoes.model.Assessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    Page<Assessment> findByAuthorId(Long authorId, Pageable pageable);

    Page<Assessment> findByDisciplineId(Long disciplineId, Pageable pageable);

    Page<Assessment> findByAuthorIdAndDisciplineId(Long authorId, Long disciplineId, Pageable pageable);

    boolean existsByCourseId(Long courseId);

    boolean existsByDisciplineId(Long disciplineId);

    boolean existsByAuthorId(Long authorId);
}
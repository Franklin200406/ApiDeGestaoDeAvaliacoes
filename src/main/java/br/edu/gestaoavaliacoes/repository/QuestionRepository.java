package br.edu.gestaoavaliacoes.repository;

import br.edu.gestaoavaliacoes.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findByDisciplineId(Long disciplineId, Pageable pageable);

    boolean existsByDisciplineId(Long disciplineId);

    boolean existsByAuthorId(Long authorId);
}
package br.edu.gestaoavaliacoes.repository;

import br.edu.gestaoavaliacoes.model.AssessmentOpenQuestion;
import br.edu.gestaoavaliacoes.model.AssessmentQuestionKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentOpenQuestionRepository
        extends JpaRepository<AssessmentOpenQuestion, AssessmentQuestionKey> {

    List<AssessmentOpenQuestion> findByIdAssessmentId(Long assessmentId);

    boolean existsByQuestionId(Long questionId);
}
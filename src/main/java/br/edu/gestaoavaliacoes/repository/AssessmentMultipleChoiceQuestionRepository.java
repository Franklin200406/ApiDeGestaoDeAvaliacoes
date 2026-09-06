package br.edu.gestaoavaliacoes.repository;

import br.edu.gestaoavaliacoes.model.AssessmentMultipleChoiceQuestion;
import br.edu.gestaoavaliacoes.model.AssessmentQuestionKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentMultipleChoiceQuestionRepository
        extends JpaRepository<AssessmentMultipleChoiceQuestion, AssessmentQuestionKey> {

    List<AssessmentMultipleChoiceQuestion> findByIdAssessmentId(Long assessmentId);

    boolean existsByQuestionId(Long questionId);
}
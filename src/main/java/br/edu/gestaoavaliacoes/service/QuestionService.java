package br.edu.gestaoavaliacoes.service;

import br.edu.gestaoavaliacoes.presentation.dto.request.QuestionRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.QuestionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    public Page<QuestionResponse> findByDiscipline(Long disciplineId, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    public QuestionResponse findById(Long id) {
        throw new UnsupportedOperationException();
    }

    public QuestionResponse create(QuestionRequest request) {
        throw new UnsupportedOperationException();
    }

    public QuestionResponse update(Long id, QuestionRequest request) {
        throw new UnsupportedOperationException();
    }

    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }
}
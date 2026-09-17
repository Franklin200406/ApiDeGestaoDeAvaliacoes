package br.edu.gestaoavaliacoes.service;

import br.edu.gestaoavaliacoes.presentation.dto.request.AssessmentAssociationRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.AssessmentRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.AssessmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AssessmentService {

    public Page<AssessmentResponse> findAll(Long disciplineId, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    public Page<AssessmentResponse> findMine(Long disciplineId, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    public AssessmentResponse findById(Long id) {
        throw new UnsupportedOperationException();
    }

    public AssessmentResponse create(AssessmentRequest request) {
        throw new UnsupportedOperationException();
    }

    public AssessmentResponse update(Long id, AssessmentRequest request) {
        throw new UnsupportedOperationException();
    }

    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    public AssessmentResponse addMultipleChoiceQuestion(Long id, AssessmentAssociationRequest request) {
        throw new UnsupportedOperationException();
    }

    public AssessmentResponse removeMultipleChoiceQuestion(Long id, Long questionId) {
        throw new UnsupportedOperationException();
    }

    public AssessmentResponse addOpenQuestion(Long id, AssessmentAssociationRequest request) {
        throw new UnsupportedOperationException();
    }

    public AssessmentResponse removeOpenQuestion(Long id, Long questionId) {
        throw new UnsupportedOperationException();
    }

    public byte[] generatePdf(Long id, Long templateId) {
        throw new UnsupportedOperationException();
    }
}
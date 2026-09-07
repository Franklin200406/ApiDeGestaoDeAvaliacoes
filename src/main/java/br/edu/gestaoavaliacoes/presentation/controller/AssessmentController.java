package br.edu.gestaoavaliacoes.presentation.controller;

import br.edu.gestaoavaliacoes.presentation.dto.request.AssessmentAssociationRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.AssessmentRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.AssessmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    @GetMapping
    public Page<AssessmentResponse> findAll(@RequestParam(required = false) Long disciplineId,
                                            @PageableDefault(size = 20) Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/mine")
    public Page<AssessmentResponse> findMine(@RequestParam(required = false) Long disciplineId,
                                             @PageableDefault(size = 20) Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{id}")
    public AssessmentResponse findById(@PathVariable Long id) {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public AssessmentResponse create(@RequestBody AssessmentRequest request) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/{id}")
    public AssessmentResponse update(@PathVariable Long id, @RequestBody AssessmentRequest request) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/{id}/multiple-choice-questions")
    public AssessmentResponse addMultipleChoiceQuestion(@PathVariable Long id,
                                                        @RequestBody AssessmentAssociationRequest request) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{id}/multiple-choice-questions/{questionId}")
    public AssessmentResponse removeMultipleChoiceQuestion(@PathVariable Long id,
                                                           @PathVariable Long questionId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/{id}/open-questions")
    public AssessmentResponse addOpenQuestion(@PathVariable Long id,
                                              @RequestBody AssessmentAssociationRequest request) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{id}/open-questions/{questionId}")
    public AssessmentResponse removeOpenQuestion(@PathVariable Long id, @PathVariable Long questionId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long id, @RequestParam Long templateId) {
        throw new UnsupportedOperationException();
    }
}
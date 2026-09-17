package br.edu.gestaoavaliacoes.presentation.controller;

import br.edu.gestaoavaliacoes.presentation.dto.request.AssessmentAssociationRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.AssessmentRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.AssessmentResponse;
import br.edu.gestaoavaliacoes.service.AssessmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping
    public Page<AssessmentResponse> findAll(@RequestParam(required = false) Long disciplineId,
                                            @PageableDefault(size = 20) Pageable pageable) {
        return assessmentService.findAll(disciplineId, pageable);
    }

    @GetMapping("/mine")
    public Page<AssessmentResponse> findMine(@RequestParam(required = false) Long disciplineId,
                                             @PageableDefault(size = 20) Pageable pageable) {
        return assessmentService.findMine(disciplineId, pageable);
    }

    @GetMapping("/{id}")
    public AssessmentResponse findById(@PathVariable Long id) {
        return assessmentService.findById(id);
    }

    @PostMapping
    public AssessmentResponse create(@RequestBody AssessmentRequest request) {
        return assessmentService.create(request);
    }

    @PutMapping("/{id}")
    public AssessmentResponse update(@PathVariable Long id, @RequestBody AssessmentRequest request) {
        return assessmentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        assessmentService.delete(id);
    }

    @PostMapping("/{id}/multiple-choice-questions")
    public AssessmentResponse addMultipleChoiceQuestion(@PathVariable Long id,
                                                        @RequestBody AssessmentAssociationRequest request) {
        return assessmentService.addMultipleChoiceQuestion(id, request);
    }

    @DeleteMapping("/{id}/multiple-choice-questions/{questionId}")
    public AssessmentResponse removeMultipleChoiceQuestion(@PathVariable Long id,
                                                           @PathVariable Long questionId) {
        return assessmentService.removeMultipleChoiceQuestion(id, questionId);
    }

    @PostMapping("/{id}/open-questions")
    public AssessmentResponse addOpenQuestion(@PathVariable Long id,
                                              @RequestBody AssessmentAssociationRequest request) {
        return assessmentService.addOpenQuestion(id, request);
    }

    @DeleteMapping("/{id}/open-questions/{questionId}")
    public AssessmentResponse removeOpenQuestion(@PathVariable Long id, @PathVariable Long questionId) {
        return assessmentService.removeOpenQuestion(id, questionId);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long id, @RequestParam Long templateId) {
        byte[] pdf = assessmentService.generatePdf(id, templateId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename("avaliacao-" + id + ".pdf").build().toString())
                .body(pdf);
    }
}

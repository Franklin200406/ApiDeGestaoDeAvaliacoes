package br.edu.gestaoavaliacoes.presentation.controller;

import br.edu.gestaoavaliacoes.presentation.dto.request.QuestionRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.QuestionResponse;
import br.edu.gestaoavaliacoes.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public Page<QuestionResponse> findByDiscipline(@RequestParam Long disciplineId,
                                                   @PageableDefault(size = 20) Pageable pageable) {
        return questionService.findByDiscipline(disciplineId, pageable);
    }

    @GetMapping("/{id}")
    public QuestionResponse findById(@PathVariable Long id) {
        return questionService.findById(id);
    }

    @PostMapping
    public QuestionResponse create(@RequestBody QuestionRequest request) {
        return questionService.create(request);
    }

    @PutMapping("/{id}")
    public QuestionResponse update(@PathVariable Long id, @RequestBody QuestionRequest request) {
        return questionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        questionService.delete(id);
    }
}

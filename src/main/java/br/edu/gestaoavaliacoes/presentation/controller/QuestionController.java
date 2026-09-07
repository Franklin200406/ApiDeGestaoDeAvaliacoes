package br.edu.gestaoavaliacoes.presentation.controller;

import br.edu.gestaoavaliacoes.presentation.dto.request.QuestionRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.QuestionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @GetMapping
    public Page<QuestionResponse> findByDiscipline(@RequestParam Long disciplineId,
                                                   @PageableDefault(size = 20) Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{id}")
    public QuestionResponse findById(@PathVariable Long id) {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public QuestionResponse create(@RequestBody QuestionRequest request) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/{id}")
    public QuestionResponse update(@PathVariable Long id, @RequestBody QuestionRequest request) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        throw new UnsupportedOperationException();
    }
}
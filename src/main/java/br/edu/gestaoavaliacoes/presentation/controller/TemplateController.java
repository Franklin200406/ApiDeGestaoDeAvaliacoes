package br.edu.gestaoavaliacoes.presentation.controller;

import br.edu.gestaoavaliacoes.presentation.dto.request.TemplateRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.TemplateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {

    @GetMapping
    public Page<TemplateResponse> findAll(@PageableDefault(size = 20) Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{id}")
    public TemplateResponse findById(@PathVariable Long id) {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public TemplateResponse create(@RequestBody TemplateRequest request) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/{id}")
    public TemplateResponse update(@PathVariable Long id, @RequestBody TemplateRequest request) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        throw new UnsupportedOperationException();
    }
}
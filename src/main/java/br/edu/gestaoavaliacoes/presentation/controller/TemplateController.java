package br.edu.gestaoavaliacoes.presentation.controller;

import br.edu.gestaoavaliacoes.presentation.dto.request.TemplateRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.TemplateResponse;
import br.edu.gestaoavaliacoes.service.TemplateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {

    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping
    public Page<TemplateResponse> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return templateService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public TemplateResponse findById(@PathVariable Long id) {
        return templateService.findById(id);
    }

    @PostMapping
    public TemplateResponse create(@RequestBody TemplateRequest request) {
        return templateService.create(request);
    }

    @PutMapping("/{id}")
    public TemplateResponse update(@PathVariable Long id, @RequestBody TemplateRequest request) {
        return templateService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        templateService.delete(id);
    }
}

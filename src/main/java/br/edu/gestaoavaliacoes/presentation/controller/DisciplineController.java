package br.edu.gestaoavaliacoes.presentation.controller;

import br.edu.gestaoavaliacoes.presentation.dto.request.DisciplineRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.DisciplineResponse;
import br.edu.gestaoavaliacoes.service.DisciplineService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/disciplines")
public class DisciplineController {

    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @GetMapping
    public Page<DisciplineResponse> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return disciplineService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public DisciplineResponse findById(@PathVariable Long id) {
        return disciplineService.findById(id);
    }

    @PostMapping
    public DisciplineResponse create(@RequestBody DisciplineRequest request) {
        return disciplineService.create(request);
    }

    @PutMapping("/{id}")
    public DisciplineResponse update(@PathVariable Long id, @RequestBody DisciplineRequest request) {
        return disciplineService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        disciplineService.delete(id);
    }
}

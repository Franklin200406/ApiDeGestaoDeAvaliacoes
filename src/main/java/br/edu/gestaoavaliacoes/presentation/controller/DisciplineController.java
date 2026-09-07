package br.edu.gestaoavaliacoes.presentation.controller;

import br.edu.gestaoavaliacoes.presentation.dto.request.DisciplineRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.DisciplineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/disciplines")
public class DisciplineController {

    @GetMapping
    public Page<DisciplineResponse> findAll(@PageableDefault(size = 20) Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{id}")
    public DisciplineResponse findById(@PathVariable Long id) {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public DisciplineResponse create(@RequestBody DisciplineRequest request) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/{id}")
    public DisciplineResponse update(@PathVariable Long id, @RequestBody DisciplineRequest request) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        throw new UnsupportedOperationException();
    }
}
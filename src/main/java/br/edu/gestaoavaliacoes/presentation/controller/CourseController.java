package br.edu.gestaoavaliacoes.presentation.controller;

import br.edu.gestaoavaliacoes.presentation.dto.request.CourseRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.CourseResponse;
import br.edu.gestaoavaliacoes.presentation.dto.response.DisciplineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @GetMapping
    public Page<CourseResponse> findAll(@PageableDefault(size = 20) Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{id}")
    public CourseResponse findById(@PathVariable Long id) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{id}/disciplines")
    public Page<DisciplineResponse> findDisciplines(@PathVariable Long id,
                                                    @PageableDefault(size = 20) Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public CourseResponse create(@RequestBody CourseRequest request) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/{id}")
    public CourseResponse update(@PathVariable Long id, @RequestBody CourseRequest request) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        throw new UnsupportedOperationException();
    }
}
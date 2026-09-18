package br.edu.gestaoavaliacoes.presentation.controller;

import br.edu.gestaoavaliacoes.presentation.dto.request.CourseRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.CourseResponse;
import br.edu.gestaoavaliacoes.presentation.dto.response.DisciplineResponse;
import br.edu.gestaoavaliacoes.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public Page<CourseResponse> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return courseService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CourseResponse findById(@PathVariable Long id) {
        return courseService.findById(id);
    }

    @GetMapping("/{id}/disciplines")
    public Page<DisciplineResponse> findDisciplines(@PathVariable Long id,
                                                    @PageableDefault(size = 20) Pageable pageable) {
        return courseService.findDisciplines(id, pageable);
    }

    @PostMapping
    public CourseResponse create(@Valid @RequestBody CourseRequest request) {
        return courseService.create(request);
    }

    @PutMapping("/{id}")
    public CourseResponse update(@PathVariable Long id, @Valid @RequestBody CourseRequest request) {
        return courseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }
}

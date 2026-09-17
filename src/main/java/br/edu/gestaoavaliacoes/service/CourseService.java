package br.edu.gestaoavaliacoes.service;

import br.edu.gestaoavaliacoes.presentation.dto.request.CourseRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.CourseResponse;
import br.edu.gestaoavaliacoes.presentation.dto.response.DisciplineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    public Page<CourseResponse> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    public CourseResponse findById(Long id) {
        throw new UnsupportedOperationException();
    }

    public Page<DisciplineResponse> findDisciplines(Long id, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    public CourseResponse create(CourseRequest request) {
        throw new UnsupportedOperationException();
    }

    public CourseResponse update(Long id, CourseRequest request) {
        throw new UnsupportedOperationException();
    }

    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }
}
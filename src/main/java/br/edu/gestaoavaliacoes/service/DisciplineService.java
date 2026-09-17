package br.edu.gestaoavaliacoes.service;

import br.edu.gestaoavaliacoes.presentation.dto.request.DisciplineRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.DisciplineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DisciplineService {

    public Page<DisciplineResponse> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    public DisciplineResponse findById(Long id) {
        throw new UnsupportedOperationException();
    }

    public DisciplineResponse create(DisciplineRequest request) {
        throw new UnsupportedOperationException();
    }

    public DisciplineResponse update(Long id, DisciplineRequest request) {
        throw new UnsupportedOperationException();
    }

    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }
}
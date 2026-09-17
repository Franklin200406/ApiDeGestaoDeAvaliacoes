package br.edu.gestaoavaliacoes.service;

import br.edu.gestaoavaliacoes.presentation.dto.request.TemplateRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.TemplateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    public Page<TemplateResponse> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    public TemplateResponse findById(Long id) {
        throw new UnsupportedOperationException();
    }

    public TemplateResponse create(TemplateRequest request) {
        throw new UnsupportedOperationException();
    }

    public TemplateResponse update(Long id, TemplateRequest request) {
        throw new UnsupportedOperationException();
    }

    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }
}
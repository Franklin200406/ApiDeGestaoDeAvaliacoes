package br.edu.gestaoavaliacoes.service;

import br.edu.gestaoavaliacoes.model.Template;
import br.edu.gestaoavaliacoes.presentation.dto.request.TemplateRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.TemplateResponse;
import br.edu.gestaoavaliacoes.repository.TemplateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class TemplateService {

    private final TemplateRepository templateRepository;

    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public Page<TemplateResponse> findAll(Pageable pageable) {
        return templateRepository.findAll(pageable).map(this::toResponse);
    }

    public TemplateResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public TemplateResponse create(TemplateRequest request) {
        Template template = new Template(request.getName(), request.getFileName());
        return toResponse(templateRepository.save(template));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public TemplateResponse update(Long id, TemplateRequest request) {
        Template template = getOrThrow(id);
        template.setName(request.getName());
        template.setFileName(request.getFileName());
        return toResponse(templateRepository.save(template));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        Template template = getOrThrow(id);
        templateRepository.delete(template);
    }

    private Template getOrThrow(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Template não encontrado"));
    }

    private TemplateResponse toResponse(Template template) {
        return new TemplateResponse(template.getId(), template.getName(), template.getFileName());
    }
}

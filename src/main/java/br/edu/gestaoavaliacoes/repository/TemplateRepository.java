package br.edu.gestaoavaliacoes.repository;

import br.edu.gestaoavaliacoes.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}
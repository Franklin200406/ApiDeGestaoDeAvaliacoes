package br.edu.gestaoavaliacoes.service;

import br.edu.gestaoavaliacoes.model.Course;
import br.edu.gestaoavaliacoes.model.Discipline;
import br.edu.gestaoavaliacoes.presentation.dto.request.DisciplineRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.DisciplineResponse;
import br.edu.gestaoavaliacoes.repository.AssessmentRepository;
import br.edu.gestaoavaliacoes.repository.CourseRepository;
import br.edu.gestaoavaliacoes.repository.DisciplineRepository;
import br.edu.gestaoavaliacoes.repository.QuestionRepository;
import br.edu.gestaoavaliacoes.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;
    private final CourseRepository courseRepository;
    private final QuestionRepository questionRepository;
    private final AssessmentRepository assessmentRepository;
    private final UserRepository userRepository;

    public DisciplineService(DisciplineRepository disciplineRepository, CourseRepository courseRepository,
                             QuestionRepository questionRepository, AssessmentRepository assessmentRepository,
                             UserRepository userRepository) {
        this.disciplineRepository = disciplineRepository;
        this.courseRepository = courseRepository;
        this.questionRepository = questionRepository;
        this.assessmentRepository = assessmentRepository;
        this.userRepository = userRepository;
    }

    public Page<DisciplineResponse> findAll(Pageable pageable) {
        return disciplineRepository.findAll(pageable).map(this::toResponse);
    }

    public DisciplineResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public DisciplineResponse create(DisciplineRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
        Discipline discipline = new Discipline(request.getName(), request.getDescription(),
                request.getWorkloadHours(), request.getShift(), course);
        return toResponse(disciplineRepository.save(discipline));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public DisciplineResponse update(Long id, DisciplineRequest request) {
        Discipline discipline = getOrThrow(id);
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
        discipline.setName(request.getName());
        discipline.setDescription(request.getDescription());
        discipline.setWorkloadHours(request.getWorkloadHours());
        discipline.setShift(request.getShift());
        discipline.setCourse(course);
        return toResponse(disciplineRepository.save(discipline));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        Discipline discipline = getOrThrow(id);
        if (questionRepository.existsByDisciplineId(id) || assessmentRepository.existsByDisciplineId(id)
                || userRepository.existsByDisciplines_Id(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Disciplina possui questões, avaliações ou usuários associados e não pode ser removida");
        }
        disciplineRepository.delete(discipline);
    }

    private Discipline getOrThrow(Long id) {
        return disciplineRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
    }

    private DisciplineResponse toResponse(Discipline discipline) {
        return new DisciplineResponse(discipline.getId(), discipline.getName(), discipline.getDescription(),
                discipline.getWorkloadHours(), discipline.getShift(), discipline.getCourse().getId());
    }
}

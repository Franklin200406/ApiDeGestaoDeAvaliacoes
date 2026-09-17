package br.edu.gestaoavaliacoes.service;

import br.edu.gestaoavaliacoes.model.Course;
import br.edu.gestaoavaliacoes.model.Discipline;
import br.edu.gestaoavaliacoes.presentation.dto.request.CourseRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.CourseResponse;
import br.edu.gestaoavaliacoes.presentation.dto.response.DisciplineResponse;
import br.edu.gestaoavaliacoes.repository.AssessmentRepository;
import br.edu.gestaoavaliacoes.repository.CourseRepository;
import br.edu.gestaoavaliacoes.repository.DisciplineRepository;
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
public class CourseService {

    private final CourseRepository courseRepository;
    private final DisciplineRepository disciplineRepository;
    private final AssessmentRepository assessmentRepository;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, DisciplineRepository disciplineRepository,
                         AssessmentRepository assessmentRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.disciplineRepository = disciplineRepository;
        this.assessmentRepository = assessmentRepository;
        this.userRepository = userRepository;
    }

    public Page<CourseResponse> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable).map(this::toResponse);
    }

    public CourseResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public Page<DisciplineResponse> findDisciplines(Long id, Pageable pageable) {
        if (!courseRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado");
        }
        return disciplineRepository.findByCourseId(id, pageable).map(this::toDisciplineResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CourseResponse create(CourseRequest request) {
        Course course = new Course(request.getName(), request.getDescription(), request.getClassFormat());
        return toResponse(courseRepository.save(course));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CourseResponse update(Long id, CourseRequest request) {
        Course course = getOrThrow(id);
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setClassFormat(request.getClassFormat());
        return toResponse(courseRepository.save(course));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        Course course = getOrThrow(id);
        if (disciplineRepository.existsByCourseId(id) || assessmentRepository.existsByCourseId(id)
                || userRepository.existsByCourses_Id(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Curso possui disciplinas, avaliações ou usuários associados e não pode ser removido");
        }
        courseRepository.delete(course);
    }

    private Course getOrThrow(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
    }

    private CourseResponse toResponse(Course course) {
        return new CourseResponse(course.getId(), course.getName(), course.getDescription(), course.getClassFormat());
    }

    private DisciplineResponse toDisciplineResponse(Discipline discipline) {
        return new DisciplineResponse(discipline.getId(), discipline.getName(), discipline.getDescription(),
                discipline.getWorkloadHours(), discipline.getShift(), discipline.getCourse().getId());
    }
}

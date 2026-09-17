package br.edu.gestaoavaliacoes.service;

import br.edu.gestaoavaliacoes.model.Course;
import br.edu.gestaoavaliacoes.model.Discipline;
import br.edu.gestaoavaliacoes.model.User;
import br.edu.gestaoavaliacoes.presentation.dto.request.ChangePasswordRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.ResetPasswordRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.UserCourseRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.UserCreateRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.UserDisciplineRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.UserUpdateRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.UserResponse;
import br.edu.gestaoavaliacoes.repository.AssessmentRepository;
import br.edu.gestaoavaliacoes.repository.CourseRepository;
import br.edu.gestaoavaliacoes.repository.DisciplineRepository;
import br.edu.gestaoavaliacoes.repository.QuestionRepository;
import br.edu.gestaoavaliacoes.repository.UserRepository;
import br.edu.gestaoavaliacoes.security.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final DisciplineRepository disciplineRepository;
    private final QuestionRepository questionRepository;
    private final AssessmentRepository assessmentRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CourseRepository courseRepository,
                       DisciplineRepository disciplineRepository, QuestionRepository questionRepository,
                       AssessmentRepository assessmentRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.disciplineRepository = disciplineRepository;
        this.questionRepository = questionRepository;
        this.assessmentRepository = assessmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::toResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public UserResponse findMe() {
        return toResponse(getOrThrow(SecurityUtils.currentUser().getId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse create(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um usuário com esse e-mail");
        }
        User user = new User(request.getEmail(), request.getName(), request.getType(),
                passwordEncoder.encode(request.getPassword()));
        return toResponse(userRepository.save(user));
    }

    public UserResponse updateMe(UserUpdateRequest request) {
        User user = getOrThrow(SecurityUtils.currentUser().getId());
        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um usuário com esse e-mail");
        }
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        return toResponse(userRepository.save(user));
    }

    public void changePassword(ChangePasswordRequest request) {
        User user = getOrThrow(SecurityUtils.currentUser().getId());
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha atual incorreta");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void resetPassword(Long id, ResetPasswordRequest request) {
        User user = getOrThrow(id);
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        User user = getOrThrow(id);
        if (questionRepository.existsByAuthorId(id) || assessmentRepository.existsByAuthorId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Usuário possui questões ou avaliações associadas e não pode ser removido");
        }
        userRepository.delete(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse addCourse(Long id, UserCourseRequest request) {
        User user = getOrThrow(id);
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
        user.getCourses().add(course);
        return toResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse removeCourse(Long id, Long courseId) {
        User user = getOrThrow(id);
        user.getCourses().removeIf(course -> course.getId().equals(courseId));
        return toResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse addDiscipline(Long id, UserDisciplineRequest request) {
        User user = getOrThrow(id);
        Discipline discipline = disciplineRepository.findById(request.getDisciplineId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
        user.getDisciplines().add(discipline);
        return toResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse removeDiscipline(Long id, Long disciplineId) {
        User user = getOrThrow(id);
        user.getDisciplines().removeIf(discipline -> discipline.getId().equals(disciplineId));
        return toResponse(userRepository.save(user));
    }

    private User getOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    private UserResponse toResponse(User user) {
        List<Long> courseIds = user.getCourses().stream().map(Course::getId).sorted().toList();
        List<Long> disciplineIds = user.getDisciplines().stream().map(Discipline::getId).sorted().toList();
        return new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getType(), courseIds, disciplineIds);
    }
}

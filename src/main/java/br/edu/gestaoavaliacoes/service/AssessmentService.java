package br.edu.gestaoavaliacoes.service;

import br.edu.gestaoavaliacoes.model.Assessment;
import br.edu.gestaoavaliacoes.model.AssessmentMultipleChoiceQuestion;
import br.edu.gestaoavaliacoes.model.AssessmentOpenQuestion;
import br.edu.gestaoavaliacoes.model.AssessmentQuestionKey;
import br.edu.gestaoavaliacoes.model.Course;
import br.edu.gestaoavaliacoes.model.Discipline;
import br.edu.gestaoavaliacoes.model.Question;
import br.edu.gestaoavaliacoes.model.User;
import br.edu.gestaoavaliacoes.model.enums.QuestionType;
import br.edu.gestaoavaliacoes.model.enums.UserType;
import br.edu.gestaoavaliacoes.pdf.AssessmentPdfGenerator;
import br.edu.gestaoavaliacoes.presentation.dto.request.AssessmentAssociationRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.AssessmentRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.AssessmentAssociationResponse;
import br.edu.gestaoavaliacoes.presentation.dto.response.AssessmentResponse;
import br.edu.gestaoavaliacoes.repository.AssessmentMultipleChoiceQuestionRepository;
import br.edu.gestaoavaliacoes.repository.AssessmentOpenQuestionRepository;
import br.edu.gestaoavaliacoes.repository.AssessmentRepository;
import br.edu.gestaoavaliacoes.repository.CourseRepository;
import br.edu.gestaoavaliacoes.repository.DisciplineRepository;
import br.edu.gestaoavaliacoes.repository.QuestionRepository;
import br.edu.gestaoavaliacoes.repository.TemplateRepository;
import br.edu.gestaoavaliacoes.repository.UserRepository;
import br.edu.gestaoavaliacoes.security.SecurityUser;
import br.edu.gestaoavaliacoes.security.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final CourseRepository courseRepository;
    private final DisciplineRepository disciplineRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final TemplateRepository templateRepository;
    private final AssessmentMultipleChoiceQuestionRepository assessmentMultipleChoiceQuestionRepository;
    private final AssessmentOpenQuestionRepository assessmentOpenQuestionRepository;

    public AssessmentService(AssessmentRepository assessmentRepository, CourseRepository courseRepository,
                             DisciplineRepository disciplineRepository, QuestionRepository questionRepository,
                             UserRepository userRepository, TemplateRepository templateRepository,
                             AssessmentMultipleChoiceQuestionRepository assessmentMultipleChoiceQuestionRepository,
                             AssessmentOpenQuestionRepository assessmentOpenQuestionRepository) {
        this.assessmentRepository = assessmentRepository;
        this.courseRepository = courseRepository;
        this.disciplineRepository = disciplineRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.templateRepository = templateRepository;
        this.assessmentMultipleChoiceQuestionRepository = assessmentMultipleChoiceQuestionRepository;
        this.assessmentOpenQuestionRepository = assessmentOpenQuestionRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<AssessmentResponse> findAll(Long disciplineId, Pageable pageable) {
        Page<Assessment> page = disciplineId != null
                ? assessmentRepository.findByDisciplineId(disciplineId, pageable)
                : assessmentRepository.findAll(pageable);
        return page.map(this::toResponse);
    }

    public Page<AssessmentResponse> findMine(Long disciplineId, Pageable pageable) {
        Long authorId = SecurityUtils.currentUser().getId();
        Page<Assessment> page = disciplineId != null
                ? assessmentRepository.findByAuthorIdAndDisciplineId(authorId, disciplineId, pageable)
                : assessmentRepository.findByAuthorId(authorId, pageable);
        return page.map(this::toResponse);
    }

    public AssessmentResponse findById(Long id) {
        Assessment assessment = getOrThrow(id);
        requireAdminOrAuthor(assessment);
        return toResponse(assessment);
    }

    @PreAuthorize("hasRole('AUTHOR')")
    public AssessmentResponse create(AssessmentRequest request) {
        SecurityUser current = SecurityUtils.currentUser();
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
        Discipline discipline = disciplineRepository.findById(request.getDisciplineId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
        validateDisciplineBelongsToCourse(discipline, course);
        if (!userRepository.existsByIdAndDisciplines_Id(current.getId(), discipline.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não está associado a essa disciplina");
        }
        User author = userRepository.getReferenceById(current.getId());
        Assessment assessment = new Assessment(request.getSemester(), request.getTeacher(),
                request.getAssessmentDate(), request.getElaborationDate(), request.getType(), request.getValue(),
                request.getMultipleChoiceQuestionCount(), request.getOpenQuestionCount(), course, discipline, author);
        return toResponse(assessmentRepository.save(assessment));
    }

    public AssessmentResponse update(Long id, AssessmentRequest request) {
        Assessment assessment = getOrThrow(id);
        requireAdminOrAuthor(assessment);
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
        Discipline discipline = disciplineRepository.findById(request.getDisciplineId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
        validateDisciplineBelongsToCourse(discipline, course);
        if (!userRepository.existsByIdAndDisciplines_Id(assessment.getAuthor().getId(), discipline.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Autor da avaliação não está associado a essa disciplina");
        }
        assessment.setCourse(course);
        assessment.setDiscipline(discipline);
        assessment.setSemester(request.getSemester());
        assessment.setTeacher(request.getTeacher());
        assessment.setAssessmentDate(request.getAssessmentDate());
        assessment.setElaborationDate(request.getElaborationDate());
        assessment.setType(request.getType());
        assessment.setValue(request.getValue());
        assessment.setMultipleChoiceQuestionCount(request.getMultipleChoiceQuestionCount());
        assessment.setOpenQuestionCount(request.getOpenQuestionCount());
        return toResponse(assessmentRepository.save(assessment));
    }

    public void delete(Long id) {
        Assessment assessment = getOrThrow(id);
        requireAdminOrAuthor(assessment);
        assessmentMultipleChoiceQuestionRepository.deleteAll(assessment.getMultipleChoiceQuestions());
        assessmentOpenQuestionRepository.deleteAll(assessment.getOpenQuestions());
        assessmentRepository.delete(assessment);
    }

    public AssessmentResponse addMultipleChoiceQuestion(Long id, AssessmentAssociationRequest request) {
        Assessment assessment = getOrThrow(id);
        requireAdminOrAuthor(assessment);
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Questão não encontrada"));
        if (question.getType() != QuestionType.MULTIPLE_CHOICE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Questão informada não é de múltipla escolha");
        }
        validateQuestionBelongsToDiscipline(question, assessment);
        AssessmentQuestionKey key = new AssessmentQuestionKey(id, question.getId());
        if (assessmentMultipleChoiceQuestionRepository.existsById(key)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Questão já associada a essa avaliação");
        }
        if (assessment.getMultipleChoiceQuestions().size() >= assessment.getMultipleChoiceQuestionCount()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Quantidade de questões de múltipla escolha já atingida");
        }
        AssessmentMultipleChoiceQuestion saved = assessmentMultipleChoiceQuestionRepository.save(
                new AssessmentMultipleChoiceQuestion(assessment, question, request.getValue()));
        assessment.getMultipleChoiceQuestions().add(saved);
        return toResponse(assessment);
    }

    public AssessmentResponse removeMultipleChoiceQuestion(Long id, Long questionId) {
        Assessment assessment = getOrThrow(id);
        requireAdminOrAuthor(assessment);
        AssessmentQuestionKey key = new AssessmentQuestionKey(id, questionId);
        if (!assessmentMultipleChoiceQuestionRepository.existsById(key)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada");
        }
        assessmentMultipleChoiceQuestionRepository.deleteById(key);
        assessment.getMultipleChoiceQuestions().removeIf(a -> a.getQuestion().getId().equals(questionId));
        return toResponse(assessment);
    }

    public AssessmentResponse addOpenQuestion(Long id, AssessmentAssociationRequest request) {
        Assessment assessment = getOrThrow(id);
        requireAdminOrAuthor(assessment);
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Questão não encontrada"));
        if (question.getType() != QuestionType.OPEN) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Questão informada não é aberta");
        }
        validateQuestionBelongsToDiscipline(question, assessment);
        AssessmentQuestionKey key = new AssessmentQuestionKey(id, question.getId());
        if (assessmentOpenQuestionRepository.existsById(key)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Questão já associada a essa avaliação");
        }
        if (assessment.getOpenQuestions().size() >= assessment.getOpenQuestionCount()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Quantidade de questões abertas já atingida");
        }
        AssessmentOpenQuestion saved = assessmentOpenQuestionRepository.save(
                new AssessmentOpenQuestion(assessment, question, request.getValue()));
        assessment.getOpenQuestions().add(saved);
        return toResponse(assessment);
    }

    public AssessmentResponse removeOpenQuestion(Long id, Long questionId) {
        Assessment assessment = getOrThrow(id);
        requireAdminOrAuthor(assessment);
        AssessmentQuestionKey key = new AssessmentQuestionKey(id, questionId);
        if (!assessmentOpenQuestionRepository.existsById(key)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada");
        }
        assessmentOpenQuestionRepository.deleteById(key);
        assessment.getOpenQuestions().removeIf(a -> a.getQuestion().getId().equals(questionId));
        return toResponse(assessment);
    }

    public byte[] generatePdf(Long id, Long templateId) {
        Assessment assessment = getOrThrow(id);
        requireAdminOrAuthor(assessment);
        if (!templateRepository.existsById(templateId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Template não encontrado");
        }
        return AssessmentPdfGenerator.generate(assessment);
    }

    private void validateDisciplineBelongsToCourse(Discipline discipline, Course course) {
        if (!discipline.getCourse().getId().equals(course.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Disciplina não pertence ao curso informado");
        }
    }

    private void validateQuestionBelongsToDiscipline(Question question, Assessment assessment) {
        if (!question.getDiscipline().getId().equals(assessment.getDiscipline().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Questão não pertence à disciplina da avaliação");
        }
    }

    private void requireAdminOrAuthor(Assessment assessment) {
        SecurityUser current = SecurityUtils.currentUser();
        if (current.getType() != UserType.ADMIN && !assessment.getAuthor().getId().equals(current.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão sobre esta avaliação");
        }
    }

    private Assessment getOrThrow(Long id) {
        return assessmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avaliação não encontrada"));
    }

    private AssessmentResponse toResponse(Assessment assessment) {
        var multipleChoiceQuestions = assessment.getMultipleChoiceQuestions().stream()
                .map(a -> new AssessmentAssociationResponse(a.getQuestion().getId(), a.getValue(),
                        a.getQuestion().getDescription()))
                .toList();
        var openQuestions = assessment.getOpenQuestions().stream()
                .map(a -> new AssessmentAssociationResponse(a.getQuestion().getId(), a.getValue(),
                        a.getQuestion().getDescription()))
                .toList();
        return new AssessmentResponse(assessment.getId(), assessment.getCourse().getId(),
                assessment.getDiscipline().getId(), assessment.getSemester(), assessment.getTeacher(),
                assessment.getAssessmentDate(), assessment.getElaborationDate(), assessment.getType(),
                assessment.getValue(), assessment.getMultipleChoiceQuestionCount(),
                assessment.getOpenQuestionCount(), multipleChoiceQuestions, openQuestions);
    }
}

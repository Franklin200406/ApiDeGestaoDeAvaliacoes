package br.edu.gestaoavaliacoes.service;

import br.edu.gestaoavaliacoes.model.Discipline;
import br.edu.gestaoavaliacoes.model.Question;
import br.edu.gestaoavaliacoes.model.QuestionOption;
import br.edu.gestaoavaliacoes.model.User;
import br.edu.gestaoavaliacoes.model.enums.QuestionType;
import br.edu.gestaoavaliacoes.model.enums.UserType;
import br.edu.gestaoavaliacoes.presentation.dto.request.OptionRequest;
import br.edu.gestaoavaliacoes.presentation.dto.request.QuestionRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.OptionResponse;
import br.edu.gestaoavaliacoes.presentation.dto.response.QuestionResponse;
import br.edu.gestaoavaliacoes.repository.AssessmentMultipleChoiceQuestionRepository;
import br.edu.gestaoavaliacoes.repository.AssessmentOpenQuestionRepository;
import br.edu.gestaoavaliacoes.repository.DisciplineRepository;
import br.edu.gestaoavaliacoes.repository.QuestionRepository;
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

import java.util.List;

@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final DisciplineRepository disciplineRepository;
    private final UserRepository userRepository;
    private final AssessmentMultipleChoiceQuestionRepository assessmentMultipleChoiceQuestionRepository;
    private final AssessmentOpenQuestionRepository assessmentOpenQuestionRepository;

    public QuestionService(QuestionRepository questionRepository, DisciplineRepository disciplineRepository,
                           UserRepository userRepository,
                           AssessmentMultipleChoiceQuestionRepository assessmentMultipleChoiceQuestionRepository,
                           AssessmentOpenQuestionRepository assessmentOpenQuestionRepository) {
        this.questionRepository = questionRepository;
        this.disciplineRepository = disciplineRepository;
        this.userRepository = userRepository;
        this.assessmentMultipleChoiceQuestionRepository = assessmentMultipleChoiceQuestionRepository;
        this.assessmentOpenQuestionRepository = assessmentOpenQuestionRepository;
    }

    public Page<QuestionResponse> findByDiscipline(Long disciplineId, Pageable pageable) {
        if (!disciplineRepository.existsById(disciplineId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada");
        }
        requireAssociatedWithDiscipline(disciplineId);
        return questionRepository.findByDisciplineId(disciplineId, pageable).map(this::toResponse);
    }

    public QuestionResponse findById(Long id) {
        Question question = getOrThrow(id);
        requireAssociatedWithDiscipline(question.getDiscipline().getId());
        return toResponse(question);
    }

    @PreAuthorize("hasRole('AUTHOR')")
    public QuestionResponse create(QuestionRequest request) {
        SecurityUser current = SecurityUtils.currentUser();
        Discipline discipline = disciplineRepository.findById(request.getDisciplineId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
        if (!userRepository.existsByIdAndDisciplines_Id(current.getId(), discipline.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Usuário não está associado a essa disciplina");
        }
        User author = userRepository.getReferenceById(current.getId());
        boolean multipleChoice = request.getType() == QuestionType.MULTIPLE_CHOICE;
        Question question = new Question(request.getType(), request.getDifficulty(), request.getDescription(),
                multipleChoice ? request.getOptionCount() : null, discipline, author);
        if (multipleChoice) {
            validateOptions(request);
            question.getOptions().addAll(buildOptions(request.getOptions(), question));
        }
        return toResponse(questionRepository.save(question));
    }

    public QuestionResponse update(Long id, QuestionRequest request) {
        Question question = getOrThrow(id);
        requireAuthorOrAdmin(question);
        Discipline discipline = disciplineRepository.findById(request.getDisciplineId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
        if (!userRepository.existsByIdAndDisciplines_Id(question.getAuthor().getId(), discipline.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Autor da questão não está associado a essa disciplina");
        }
        question.setType(request.getType());
        question.setDifficulty(request.getDifficulty());
        question.setDescription(request.getDescription());
        question.setDiscipline(discipline);
        question.getOptions().clear();
        boolean multipleChoice = request.getType() == QuestionType.MULTIPLE_CHOICE;
        if (multipleChoice) {
            validateOptions(request);
            question.setOptionCount(request.getOptionCount());
            question.getOptions().addAll(buildOptions(request.getOptions(), question));
        } else {
            question.setOptionCount(null);
        }
        return toResponse(questionRepository.save(question));
    }

    public void delete(Long id) {
        Question question = getOrThrow(id);
        requireAuthorOrAdmin(question);
        if (assessmentMultipleChoiceQuestionRepository.existsByQuestionId(id)
                || assessmentOpenQuestionRepository.existsByQuestionId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Questão já utilizada em uma avaliação e não pode ser removida");
        }
        questionRepository.delete(question);
    }

    private void requireAssociatedWithDiscipline(Long disciplineId) {
        SecurityUser current = SecurityUtils.currentUser();
        if (current.getType() != UserType.ADMIN
                && !userRepository.existsByIdAndDisciplines_Id(current.getId(), disciplineId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não está associado a essa disciplina");
        }
    }

    private void requireAuthorOrAdmin(Question question) {
        SecurityUser current = SecurityUtils.currentUser();
        if (current.getType() != UserType.ADMIN && !question.getAuthor().getId().equals(current.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão sobre esta questão");
        }
    }

    private void validateOptions(QuestionRequest request) {
        List<OptionRequest> options = request.getOptions();
        if (request.getOptionCount() == null || options == null || options.size() != request.getOptionCount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Quantidade de opções informada não corresponde às opções enviadas");
        }
        long correctCount = options.stream().filter(o -> Boolean.TRUE.equals(o.getCorrect())).count();
        if (correctCount != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A questão deve ter exatamente uma opção marcada como correta");
        }
    }

    private List<QuestionOption> buildOptions(List<OptionRequest> options, Question question) {
        return options.stream()
                .map(option -> new QuestionOption(option.getText(), option.getCorrect(), question))
                .toList();
    }

    private Question getOrThrow(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Questão não encontrada"));
    }

    private QuestionResponse toResponse(Question question) {
        List<OptionResponse> options = question.getOptions().stream()
                .map(option -> new OptionResponse(option.getId(), option.getText(), option.getCorrect()))
                .toList();
        Long correctOptionId = options.stream()
                .filter(option -> Boolean.TRUE.equals(option.getCorrect()))
                .map(OptionResponse::getId)
                .findFirst()
                .orElse(null);
        return new QuestionResponse(question.getId(), question.getType(), question.getDifficulty(),
                question.getDescription(), question.getDiscipline().getId(), question.getOptionCount(),
                options, correctOptionId);
    }
}

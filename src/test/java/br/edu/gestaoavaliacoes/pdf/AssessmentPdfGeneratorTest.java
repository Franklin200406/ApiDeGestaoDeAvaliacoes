package br.edu.gestaoavaliacoes.pdf;

import br.edu.gestaoavaliacoes.model.Assessment;
import br.edu.gestaoavaliacoes.model.AssessmentMultipleChoiceQuestion;
import br.edu.gestaoavaliacoes.model.AssessmentOpenQuestion;
import br.edu.gestaoavaliacoes.model.Course;
import br.edu.gestaoavaliacoes.model.Discipline;
import br.edu.gestaoavaliacoes.model.Question;
import br.edu.gestaoavaliacoes.model.QuestionOption;
import br.edu.gestaoavaliacoes.model.User;
import br.edu.gestaoavaliacoes.model.enums.AssessmentType;
import br.edu.gestaoavaliacoes.model.enums.ClassFormat;
import br.edu.gestaoavaliacoes.model.enums.Difficulty;
import br.edu.gestaoavaliacoes.model.enums.QuestionType;
import br.edu.gestaoavaliacoes.model.enums.Shift;
import br.edu.gestaoavaliacoes.model.enums.UserType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssessmentPdfGeneratorTest {

    private static final String TEMPLATE_HTML = """
            <!DOCTYPE html>
            <html xmlns:th="http://www.thymeleaf.org">
            <head><meta charset="UTF-8"/></head>
            <body>
                <h1>Avaliação <span th:text="${assessment.type}"></span></h1>
                <p>Curso: <span th:text="${assessment.course.name}"></span></p>
                <p>Disciplina: <span th:text="${assessment.discipline.name}"></span></p>
                <div th:each="mcq, iter : ${assessment.multipleChoiceQuestions}">
                    <p th:text="${iter.count} + ') (' + ${mcq.value} + ') ' + ${mcq.question.description}"></p>
                    <ul>
                        <li th:each="option : ${mcq.question.options}" th:text="${option.text}"></li>
                    </ul>
                </div>
                <div th:each="oq, iter : ${assessment.openQuestions}">
                    <p th:text="${iter.count} + ') (' + ${oq.value} + ') ' + ${oq.question.description}"></p>
                </div>
            </body>
            </html>
            """;

    @Test
    void generatesAValidPdfFromTheTemplate() {
        Course course = new Course("Engenharia de Software", "Curso de exemplo", ClassFormat.PRESENTIAL);
        Discipline discipline = new Discipline("Programação Orientada a Objetos", "Disciplina de exemplo",
                80, Shift.NIGHT, course);
        User author = new User("author@example.com", "Autor de Conteúdo", UserType.AUTHOR, "hash");

        Question multipleChoiceQuestion = new Question(QuestionType.MULTIPLE_CHOICE, Difficulty.EASY,
                "Qual das alternativas NÃO é um pilar da orientação a objetos?", 4, discipline, author);
        multipleChoiceQuestion.setOptions(List.of(
                new QuestionOption("Encapsulamento", false, multipleChoiceQuestion),
                new QuestionOption("Herança", false, multipleChoiceQuestion),
                new QuestionOption("Polimorfismo", false, multipleChoiceQuestion),
                new QuestionOption("Recursão", true, multipleChoiceQuestion)));

        Question openQuestion = new Question(QuestionType.OPEN, Difficulty.MEDIUM,
                "Explique o conceito de polimorfismo.", null, discipline, author);

        Assessment assessment = new Assessment("2026.1", "Prof. Exemplo", LocalDate.of(2026, 6, 15),
                LocalDate.of(2026, 5, 10), AssessmentType.AV1, new BigDecimal("10.00"), 1, 1,
                course, discipline, author);
        assessment.setMultipleChoiceQuestions(List.of(
                new AssessmentMultipleChoiceQuestion(assessment, multipleChoiceQuestion, new BigDecimal("2.50"))));
        assessment.setOpenQuestions(List.of(
                new AssessmentOpenQuestion(assessment, openQuestion, new BigDecimal("7.50"))));

        byte[] pdf = new AssessmentPdfGenerator().generate(assessment, TEMPLATE_HTML);

        assertTrue(pdf.length > 0, "PDF bytes should not be empty");
        assertEquals("%PDF-", new String(pdf, 0, 5, StandardCharsets.US_ASCII));
    }
}

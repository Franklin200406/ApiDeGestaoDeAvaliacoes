package br.edu.gestaoavaliacoes.pdf;

import br.edu.gestaoavaliacoes.model.Assessment;
import br.edu.gestaoavaliacoes.model.AssessmentMultipleChoiceQuestion;
import br.edu.gestaoavaliacoes.model.AssessmentOpenQuestion;
import br.edu.gestaoavaliacoes.model.QuestionOption;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;

public final class AssessmentPdfGenerator {

    private static final float MARGIN = 50f;
    private static final float LEADING = 16f;

    private AssessmentPdfGenerator() {
    }

    public static byte[] generate(Assessment assessment) {
        try (PDDocument document = new PDDocument()) {
            PageWriter writer = new PageWriter(document);

            writer.line("Avaliação - " + assessment.getType());
            writer.line("Curso: " + assessment.getCourse().getName());
            writer.line("Disciplina: " + assessment.getDiscipline().getName());
            writer.line("Docente: " + assessment.getTeacher());
            writer.line("Semestre: " + assessment.getSemester());
            writer.line("Data da avaliação: " + assessment.getAssessmentDate());
            writer.line("Valor total: " + assessment.getValue());
            writer.blank();

            int number = 1;
            for (AssessmentMultipleChoiceQuestion association : assessment.getMultipleChoiceQuestions()) {
                writer.question(number++, association.getValue(), association.getQuestion().getDescription());
                for (QuestionOption option : association.getQuestion().getOptions()) {
                    writer.line("   ( ) " + option.getText());
                }
                writer.blank();
            }
            for (AssessmentOpenQuestion association : assessment.getOpenQuestions()) {
                writer.question(number++, association.getValue(), association.getQuestion().getDescription());
                writer.blank();
            }

            writer.close();

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            document.save(output);
            return output.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException("Falha ao gerar o PDF da avaliação", e);
        }
    }

    private static final class PageWriter {
        private final PDDocument document;
        private final PDFont font;
        private PDPageContentStream stream;
        private float cursorY;

        PageWriter(PDDocument document) throws IOException {
            this.document = document;
            this.font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            newPage();
        }

        void line(String text) throws IOException {
            if (cursorY <= MARGIN) {
                newPage();
            }
            stream.showText(sanitize(text));
            stream.newLineAtOffset(0, -LEADING);
            cursorY -= LEADING;
        }

        void question(int number, BigDecimal value, String description) throws IOException {
            line(number + ") (" + value + ") " + description);
        }

        void blank() throws IOException {
            if (cursorY <= MARGIN) {
                newPage();
                return;
            }
            stream.newLineAtOffset(0, -LEADING);
            cursorY -= LEADING;
        }

        void close() throws IOException {
            stream.endText();
            stream.close();
        }

        private void newPage() throws IOException {
            if (stream != null) {
                stream.endText();
                stream.close();
            }
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            stream = new PDPageContentStream(document, page);
            stream.setFont(font, 11);
            cursorY = page.getMediaBox().getHeight() - MARGIN;
            stream.beginText();
            stream.newLineAtOffset(MARGIN, cursorY);
        }

        private String sanitize(String text) {
            return text == null ? "" : text.replaceAll("\\s+", " ");
        }
    }
}

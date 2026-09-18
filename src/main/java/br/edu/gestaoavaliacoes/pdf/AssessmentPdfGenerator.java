package br.edu.gestaoavaliacoes.pdf;

import br.edu.gestaoavaliacoes.model.Assessment;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Renders an assessment as a PDF by processing an admin-registered HTML
 * template (Thymeleaf syntax) with the assessment's data and converting the
 * resulting HTML into a PDF document.
 */
@Component
public class AssessmentPdfGenerator {

    private final TemplateEngine templateEngine;

    public AssessmentPdfGenerator() {
        StringTemplateResolver templateResolver = new StringTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(templateResolver);
        this.templateEngine = engine;
    }

    public byte[] generate(Assessment assessment, String templateContent) {
        String html = renderHtml(assessment, templateContent);
        return renderPdf(html);
    }

    private String renderHtml(Assessment assessment, String templateContent) {
        Context context = new Context();
        context.setVariable("assessment", assessment);
        try {
            return templateEngine.process(templateContent, context);
        } catch (RuntimeException e) {
            throw new IllegalStateException("Falha ao processar o template do PDF: " + e.getMessage(), e);
        }
    }

    private byte[] renderPdf(String html) {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(output);
            builder.run();
            return output.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException("Falha ao gerar o PDF da avaliação", e);
        }
    }
}

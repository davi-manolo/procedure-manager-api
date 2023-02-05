package com.procedure.manager.service.creator;

import com.lowagie.text.DocumentException;
import com.procedure.manager.domain.exception.PdfException;
import com.procedure.manager.domain.vo.DataContentForFileVo;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.procedure.manager.domain.enumeration.ExceptionMessage.PDF_IO_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
public class PdfCreator {

    private final TemplateEngine templateEngine;

    public PdfCreator() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setPrefix("templates/");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    public byte[] create(DataContentForFileVo dataContentForFileVo) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Context context = new Context();
            context.setVariable("data", dataContentForFileVo);
            String html = templateEngine.process("report", context);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();

        } catch (DocumentException | IOException e) {
            throw new PdfException(INTERNAL_SERVER_ERROR, PDF_IO_ERROR);
        }

    }

}

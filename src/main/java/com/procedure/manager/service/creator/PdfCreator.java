package com.procedure.manager.service.creator;

import com.lowagie.text.DocumentException;
import com.procedure.manager.domain.exception.PdfException;
import com.procedure.manager.domain.vo.DataContentForFileVo;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.lowagie.text.pdf.BaseFont.EMBEDDED;
import static com.lowagie.text.pdf.BaseFont.IDENTITY_H;
import static com.procedure.manager.domain.enumeration.ExceptionMessage.PDF_IO_ERROR;
import static org.apache.commons.codec.CharEncoding.UTF_8;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
public class PdfCreator {

    private final TemplateEngine templateEngine;

    public PdfCreator() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setPrefix("templates/");
        templateResolver.setCharacterEncoding(UTF_8);
        templateResolver.setTemplateMode(TemplateMode.HTML);

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    public byte[] create(DataContentForFileVo dataContentForFileVo) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Context context = new Context();
            context.setVariable("data", dataContentForFileVo);
            String html = templateEngine.process("report", context);
            String xhtml = convertToXhtml(html);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(xhtml);
            renderer.getFontResolver().addFont("Inter-Regular.ttf", IDENTITY_H, EMBEDDED);
            renderer.layout();
            renderer.createPDF(outputStream);

            return outputStream.toByteArray();

        } catch (DocumentException | IOException e) {
            throw new PdfException(INTERNAL_SERVER_ERROR, PDF_IO_ERROR);
        }

    }

    private String convertToXhtml(String html) {
        try(ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Tidy tidy = new Tidy();
            tidy.setShowErrors(0);
            tidy.setQuiet(true);
            tidy.setShowWarnings(false);
            tidy.setInputEncoding(UTF_8);
            tidy.setOutputEncoding(UTF_8);
            tidy.setXHTML(true);
            tidy.parseDOM(inputStream, outputStream);
            return outputStream.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PdfException(INTERNAL_SERVER_ERROR, PDF_IO_ERROR);
        }
    }

}

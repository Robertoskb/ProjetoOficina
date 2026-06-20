package br.edu.ufersa.oficina.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import br.edu.ufersa.oficina.model.Entity.Order;
import br.edu.ufersa.oficina.model.Entity.Part;
import br.edu.ufersa.oficina.model.Entity.Service;
import br.edu.ufersa.oficina.model.Entity.Treatment;

public class ReportGenerator {

    private static final PDType1Font FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
    private static final PDType1Font FONT_BOLD = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final float MARGIN = 50f;
    private static final float LINE_HEIGHT = 14f;

    public static <T extends Treatment> void generateReport(String title, List<T> treatments, Path destination) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.LETTER);
            document.addPage(page);
            PDPageContentStream content = new PDPageContentStream(document, page);

            int pageNum = 1;
            float y = page.getMediaBox().getHeight() - MARGIN;
            float maxWidth = page.getMediaBox().getWidth() - MARGIN * 2;

            // Title
            content.beginText();
            content.setFont(FONT_BOLD, 14);
            content.newLineAtOffset(MARGIN, y);
            content.showText(title == null ? "Relatório" : title);
            content.endText();

            y -= LINE_HEIGHT;

            // Generated date
            content.beginText();
            content.setFont(FONT, 10);
            content.newLineAtOffset(MARGIN, y);
            content.showText("Gerado em: " + LocalDate.now().format(DATE_FORMAT));
            content.endText();

            y -= LINE_HEIGHT * 1.5f;

            // Summary
            content.beginText();
            content.setFont(FONT, 10);
            content.newLineAtOffset(MARGIN, y);
            content.showText("Registros: " + treatments.size() + "    |    Valor total: R$ " + String.format("%.2f", treatments.stream().mapToDouble(Treatment::getPrice).sum()));
            content.endText();

            y -= LINE_HEIGHT * 1.5f;

            for (Treatment t : treatments) {
                // check page space
                float needed = LINE_HEIGHT * 6 + LINE_HEIGHT * (Math.max(1, t.getParts() == null ? 0 : t.getParts().size()))
                        + LINE_HEIGHT * (Math.max(1, t.getServices() == null ? 0 : t.getServices().size())) + 20;
                if (y - needed < MARGIN) {
                    // footer then new page
                    addFooter(content, page, pageNum);
                    content.close();
                    page = new PDPage(PDRectangle.LETTER);
                    document.addPage(page);
                    content = new PDPageContentStream(document, page);
                    pageNum++;
                    y = page.getMediaBox().getHeight() - MARGIN;
                }

                // Print simple lines
                y = printWrapped(content, "ID: " + t.getId(), FONT_BOLD, 11, MARGIN, y, maxWidth);
                y = printWrapped(content, "Veículo: " + safe(t.getCar()), FONT, 10, MARGIN, y, maxWidth);
                y = printWrapped(content, "Data início: " + (t.getDate_start() == null ? "-" : t.getDate_start().format(DATE_FORMAT)), FONT, 10, MARGIN, y, maxWidth);
                y = printWrapped(content, "Data fim: " + (t.getDate_finish() == null ? "-" : t.getDate_finish().format(DATE_FORMAT)), FONT, 10, MARGIN, y, maxWidth);
                y = printWrapped(content, String.format("Valor: R$ %.2f", t.getPrice()), FONT, 10, MARGIN, y, maxWidth);
                if (t.getClass() == Order.class) {
                    Order o = (Order) t;
                    y = printWrapped(content, "Pago: " + (o.isCompleted() ? "Sim" : "Não"), FONT, 10, MARGIN, y, maxWidth);
                }

                // Parts
                y = printWrapped(content, "Peças:", FONT_BOLD, 11, MARGIN, y, maxWidth);
                if (t.getParts() == null || t.getParts().isEmpty()) {
                    y = printWrapped(content, " - (nenhuma)", FONT, 10, MARGIN + 10, y, maxWidth - 10);
                } else {
                    for (Part p : t.getParts()) {
                        y = printWrapped(content, " - " + safe(p.getName()), FONT, 10, MARGIN + 10, y, maxWidth - 10);
                    }
                }

                // Services
                y = printWrapped(content, "Serviços:", FONT_BOLD, 11, MARGIN, y, maxWidth);
                if (t.getServices() == null || t.getServices().isEmpty()) {
                    y = printWrapped(content, " - (nenhum)", FONT, 10, MARGIN + 10, y, maxWidth - 10);
                } else {
                    for (Service s : t.getServices()) {
                        y = printWrapped(content, " - " + safe(s.getName()), FONT, 10, MARGIN + 10, y, maxWidth - 10);
                    }
                }

                // separator
                y -= 6;
                content.setStrokingColor(0.8f);
                content.moveTo(MARGIN, y);
                content.lineTo(page.getMediaBox().getWidth() - MARGIN, y);
                content.stroke();
                y -= LINE_HEIGHT;

                // reset color to black for text
                content.setNonStrokingColor(0f, 0f, 0f);
            }

            // footer on last page
            addFooter(content, page, pageNum);
            content.close();
            document.save(destination.toFile());
        }
    }

    private static float printWrapped(PDPageContentStream content, String text, PDType1Font font, int fontSize, float x, float y, float maxWidth) throws IOException {
        List<String> lines = simpleWrap(text, font, fontSize, maxWidth);
        for (String line : lines) {
            content.beginText();
            content.setFont(font, fontSize);
            content.newLineAtOffset(x, y);
            content.showText(line);
            content.endText();
            y -= LINE_HEIGHT;
        }
        return y;
    }

    private static List<String> simpleWrap(String text, PDType1Font font, int fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        if (text == null) return lines;
        String[] words = text.split(" ");
        StringBuilder cur = new StringBuilder();
        for (String w : words) {
            String test = cur.length() == 0 ? w : cur + " " + w;
            float width = font.getStringWidth(test) / 1000 * fontSize;
            if (width > maxWidth && cur.length() > 0) {
                lines.add(cur.toString());
                cur = new StringBuilder(w);
            } else {
                if (cur.length() > 0) cur.append(' ');
                cur.append(w);
            }
        }
        if (cur.length() > 0) lines.add(cur.toString());
        return lines;
    }

    private static void addFooter(PDPageContentStream content, PDPage page, int pageNum) throws IOException {
        PDRectangle media = page.getMediaBox();
        float pageWidth = media.getWidth();
        content.beginText();
        content.setFont(FONT, 9);
        String footer = "Página " + pageNum;
        float footerWidth = FONT.getStringWidth(footer) / 1000 * 9;
        content.newLineAtOffset((pageWidth - footerWidth) / 2, 28);
        content.showText(footer);
        content.endText();
    }

    private static String safe(Object o) {
        return o == null ? "-" : o.toString();
    }
}
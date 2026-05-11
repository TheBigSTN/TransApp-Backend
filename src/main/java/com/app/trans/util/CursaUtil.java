package com.app.trans.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import com.app.trans.models.Anexa;
import com.app.trans.models.Client;
import com.app.trans.models.Company;
import com.app.trans.models.Cursa;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CursaUtil {

    public InputStreamResource generatorPdfItext(Anexa anexa) {

        List<Cursa> cursaList = anexa.getCurse();
        Company company = (cursaList != null && !cursaList.isEmpty()) ? cursaList.get(0).getCompany() : null;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 10, 10, 32, 10);

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            // PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));

            document.open();
            Font font = new Font(Font.FontFamily.HELVETICA, 12);

            String companyName = company != null ? company.getName() : "________________";
            String companyDetails = company != null ? "Nr. Ord. Reg. Com. J_23_/_162_/_2008 CUI R O 23116668______" : ""; //""Nr. Ord. Reg. Com. " + company.getRegCom() + " CUI " + company.getCui() : "________________";

            document.add(new Paragraph("                " + companyName, font));
            document.add(new Paragraph("                " + companyDetails, font));
            document.add(new Paragraph(" ", font));
            document.add(new Paragraph(" ", font));

            Paragraph title = new Paragraph("ANEXA LA FACTURA NR. _______________/Data:____________", font);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" ", font));
            document.add(new Paragraph(" ", font));

            PdfPTable table = new PdfPTable(8);
            float[] columnWidths = { 1.2f, 1.9f, 1.8f, 4f, 1.2f, 1.5f, 2f, 2f };
            table.setWidths(columnWidths);

            addTableHeader(table);
            Font rowFont = new Font(font);
            rowFont.setSize(9);
            addRows(table, cursaList, rowFont);
            addLastLine(table, anexa);
            document.add(table);

            document.add(new Paragraph(" ", font));
            document.add(new Paragraph(" ", font));

            float totalCuTva = (anexa.getValoare() != null ? anexa.getValoare() : 0f) + (anexa.getTva() != null ? anexa.getTva() : 0f);
            document.add(new Paragraph(
                    "                    SEMNATURA SI STAMPILA FURNIZORULUI:              Total cu TVA: ___"
                            + String.format("%.2f", totalCuTva) + "___ ",
                    font));
            document.add(new Paragraph("                    " + companyName, font));

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            // Close the document
            document.close();
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        // Return an InputStreamResource wrapping the ByteArrayInputStream
        return new InputStreamResource(byteArrayInputStream);
    }

    private void addLastLine(PdfPTable table, Anexa anexa) {
        float cellHeight = 30f;
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
        addCell(table, "", Element.ALIGN_CENTER, cellHeight, headerFont);

        PdfPCell totalCell = new PdfPCell(new Paragraph("TOTAL", headerFont));
        totalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        totalCell.setFixedHeight(cellHeight + 0.3f); // Set the height to span all rows
        totalCell.setColspan(3); // Set the number of columns to span horizontally
        table.addCell(totalCell); // Add merged cell

        // Add total values to the table
        addCell(table, String.valueOf(anexa.getKmTotal()), Element.ALIGN_CENTER, cellHeight, headerFont); // Total Km
        addCell(table, String.valueOf(anexa.getTarifMediu()), Element.ALIGN_CENTER, cellHeight, headerFont); // Total
                                                                                                             // Tarif
        addCell(table, String.valueOf(anexa.getValoare()), Element.ALIGN_CENTER, cellHeight, headerFont); // Total
                                                                                                          // Valoare
        addCell(table, String.valueOf(anexa.getTva()), Element.ALIGN_CENTER, cellHeight, headerFont); // Total TVA

        // Add a new line at the end of the table
        table.completeRow();
    }

    private void addCell(PdfPTable table, String content, int alignment, float cellHeight, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(content, font));
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(cellHeight);
        table.addCell(cell);
    }

    private void addRows(PdfPTable table, List<Cursa> cursaList, Font font) {
        Integer nr = 1;
        float cellHeight = 28f;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        for (Cursa cursa : cursaList) {
            addCell(table, nr.toString(), Element.ALIGN_CENTER, cellHeight, font);
            addCell(table, cursa.getDataEfectuare().format(formatter), Element.ALIGN_CENTER, cellHeight, font);
            addCell(table, cursa.getMasina().getNumar(), Element.ALIGN_CENTER, cellHeight, font);
            addCell(table, cursa.getLivrare(), Element.ALIGN_LEFT, cellHeight, font);
            addCell(table, String.valueOf(cursa.getKm()), Element.ALIGN_CENTER, cellHeight, font);
            addCell(table, String.valueOf(cursa.getTarif()), Element.ALIGN_CENTER, cellHeight, font);
            addCell(table, String.valueOf(cursa.getTarif() * cursa.getKm()), Element.ALIGN_CENTER, cellHeight, font);
            addCell(table, String.valueOf(cursa.getTarif() * cursa.getKm() * 0.19), Element.ALIGN_CENTER, cellHeight,
                    font);
            nr++;
        }
    }

    private void addTableHeader(PdfPTable table) {
        float cellHeight = 35f;
        String[] headers = { "Nr. crt.", "DATA", "AUTO", "RUTA", "Km", "Tarif", "Valoare", "TVA 19%" };
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD); // Define font for headers

        for (String header : headers) {
            addCell(table, header, Element.ALIGN_CENTER, cellHeight, headerFont); // Pass headerFont to addCell
        }
    }

    public Float calculateValoare(List<Cursa> cursaList) {
        return (float) cursaList.stream()
                .mapToDouble(cursa -> cursa.getKm() * cursa.getTarif())
                .sum();
    }

    public Float calculateTva(List<Cursa> cursaList) {
        Optional<Float> valoare = Optional.ofNullable(calculateValoare(cursaList));
        return valoare.map(v -> v * 0.19f).orElse(0f);
    }

    public Float calculateTarifMediu(List<Cursa> cursaList) {
        OptionalDouble average = cursaList.stream()
                .mapToDouble(Cursa::getTarif)
                .average();
        return (float) average.orElse(0.0);
    }

    public Integer calculateKmTotal(List<Cursa> cursaList) {
        return cursaList.stream()
                .mapToInt(Cursa::getKm)
                .sum();
    }

    public Client checkSameClient(List<Cursa> cursaList) {
        if (cursaList.isEmpty()) {
            log.error("List of client associated to this Anexa is null");
            return null; // If the list is empty, return null
        }

        Client firstClient = cursaList.get(0).getClient(); // Get the client of the first Cursa
        for (Cursa cursa : cursaList) {
            if (!cursa.getClient().equals(firstClient)) {
                log.error(
                        "List of client associated to this Anexa, have different Client, thay need to have the same Client");
                return null; // If any client is different, return null
            }
        }
        return firstClient; // If all clients are the same, return the client
    }

}

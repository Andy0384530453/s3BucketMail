package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class PdfTicketService {

  private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private static final DateTimeFormatter DATETIME_FMT =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

  @SneakyThrows
  public File generateTicket(User user, Course course, UUID suscribeId) {
    var file = File.createTempFile("ticket-", ".pdf");
    file.deleteOnExit();

    var document = new Document(PageSize.A4);
    PdfWriter.getInstance(document, new FileOutputStream(file));
    document.open();

    // --- Title ---
    var titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.DARK_GRAY);
    var title = new Paragraph("Confirmation d'inscription", titleFont);
    title.setAlignment(Paragraph.ALIGN_CENTER);
    document.add(title);
    document.add(new Paragraph(" "));

    // --- Reference line ---
    var refFont = FontFactory.getFont(FontFactory.COURIER, 10, Color.GRAY);
    document.add(new Paragraph("R\u00e9f\u00e9rence : " + suscribeId.toString(), refFont));
    document.add(new Paragraph(" "));

    // --- Info table ---
    var table = new PdfPTable(2);
    table.setWidthPercentage(100);
    table.setSpacingBefore(10);
    table.setSpacingAfter(10);

    // Header cell style
    var headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.WHITE);
    var headerBg = new Color(70, 130, 180);

    addCell(table, "Informations \u00e9tudiant", headerFont, headerBg);
    addCell(table, "D\u00e9tails du cours", headerFont, headerBg);

    var labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
    var valueFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

    // Left column — Student
    addLabelCell(table, "Nom", labelFont);
    addValueCell(table, user.getFirstName() + " " + user.getLastName(), valueFont);

    addLabelCell(table, "Email", labelFont);
    addValueCell(table, user.getMail(), valueFont);

    // Right column — Course
    addLabelCell(table, "Cours", labelFont);
    addValueCell(table, course.getCourseName(), valueFont);

    String debut =
        course.getStartDate() != null
            ? course.getStartDate().atZone(ZoneId.of("UTC")).format(DATE_FMT)
            : "-";
    addLabelCell(table, "D\u00e9but", labelFont);
    addValueCell(table, debut, valueFont);

    String fin =
        course.getEndDate() != null
            ? course.getEndDate().atZone(ZoneId.of("UTC")).format(DATE_FMT)
            : "-";
    addLabelCell(table, "Fin", labelFont);
    addValueCell(table, fin, valueFont);

    document.add(table);

    // --- Footer ---
    document.add(new Paragraph(" "));
    var now = ZonedDateTime.now(ZoneId.of("UTC")).format(DATETIME_FMT);
    var footerFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Color.LIGHT_GRAY);
    document.add(new Paragraph("G\u00e9n\u00e9r\u00e9 le " + now, footerFont));

    document.close();
    return file;
  }

  private void addCell(PdfPTable table, String text, Font font, Color bg) {
    var cell = new PdfPCell(new Phrase(text, font));
    cell.setBackgroundColor(bg);
    cell.setPadding(6);
    cell.setBorderColor(Color.LIGHT_GRAY);
    table.addCell(cell);
  }

  private void addLabelCell(PdfPTable table, String text, Font font) {
    var cell = new PdfPCell(new Phrase(text, font));
    cell.setPadding(5);
    cell.setBorderColor(Color.LIGHT_GRAY);
    cell.setBackgroundColor(new Color(245, 245, 245));
    table.addCell(cell);
  }

  private void addValueCell(PdfPTable table, String text, Font font) {
    var cell = new PdfPCell(new Phrase(text, font));
    cell.setPadding(5);
    cell.setBorderColor(Color.LIGHT_GRAY);
    table.addCell(cell);
  }
}

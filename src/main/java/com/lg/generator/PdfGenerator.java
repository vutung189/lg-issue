package com.lg.generator;

import com.lg.model.LgIssue;
import com.lg.utils.DateUtil;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.DocumentException;

public class PdfGenerator {

  public ByteArrayInputStream generate(List<LgIssue> issues, HttpServletResponse response)
      throws DocumentException, IOException {
    // Creating the Object of Document
    Document document = new Document(PageSize.A4);
    // Getting instance of PdfWriter
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    PdfWriter.getInstance(document, bos);
    // Opening the created document to change it
    document.open();
    // Creating font
    // Setting font style and size
    Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
    fontTiltle.setSize(20);
    // Creating paragraph
    Paragraph paragraph1 = new Paragraph("LG Issue Report", fontTiltle);
    // Aligning the paragraph in the document
    paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
    // Adding the created paragraph in the document
    document.add(paragraph1);
    // Creating a table of the 8columns
    PdfPTable table = new PdfPTable(8);
    // Setting width of the table, its columns and spacing
    table.setWidthPercentage(100f);
    table.setWidths(new int[]{1, 3, 3, 3, 3, 3, 3, 3});
    table.setSpacingBefore(5);
    // Create Table Cells for the table header
    PdfPCell cell = new PdfPCell();
    // Setting the background color and padding of the table cell
    cell.setBackgroundColor(CMYKColor.LIGHT_GRAY);
    cell.setPadding(5);
    // Creating font
    // Setting font style and size
    Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
    font.setColor(CMYKColor.WHITE);

    // Adding headings in the created table cell or  header
    // Adding Cell to table
    cell.setPhrase(new Phrase("ID", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("LG Reference", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("LG Type", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("LG Issue Date", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("Amount & Ccy", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("Applicant Name", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("IBAN#", font));
    table.addCell(cell);
    cell.setPhrase(new Phrase("Status", font));
    table.addCell(cell);

    for (LgIssue lgIssue : issues) {
      table.addCell(String.valueOf(lgIssue.getId()));
      table.addCell(lgIssue.getLgNumber());
      table.addCell(lgIssue.getLgType());
      table.addCell(DateUtil.getDateTimeString(lgIssue.getIssueDate()));
      table.addCell(lgIssue.getAmount() + " " + lgIssue.getCurrency());
      table.addCell(lgIssue.getEntityName());
      table.addCell(lgIssue.getAppIban());
      table.addCell(lgIssue.getStatus());
    }

    // Adding the created table to the document
    document.add(table);
    // Closing the document
    document.close();

    byte[] barray = bos.toByteArray();
    return new ByteArrayInputStream(barray);
  }
}

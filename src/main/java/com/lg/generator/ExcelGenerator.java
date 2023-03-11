package com.lg.generator;

import com.lg.model.LgIssue;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerator {

  private List<LgIssue> issues;
  private XSSFWorkbook workbook;
  private XSSFSheet sheet;

  public ExcelGenerator(List<LgIssue> issues) {
    this.issues = issues;
    workbook = new XSSFWorkbook();
  }

  private void writeHeader() {
    sheet = workbook.createSheet("Report LG Issue");
    Row row = sheet.createRow(0);
    CellStyle style = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    font.setBold(true);
    font.setFontHeight(16);
    style.setFont(font);
    createCell(row, 0, "ID", style);
    createCell(row, 1, "LG Reference", style);
    createCell(row, 2, "Amount", style);
    createCell(row, 3, "LG Type", style);
  }

  private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
    sheet.autoSizeColumn(columnCount);
    Cell cell = row.createCell(columnCount);
    if (valueOfCell instanceof Integer) {
      cell.setCellValue((Integer) valueOfCell);
    } else if (valueOfCell instanceof Long) {
      cell.setCellValue((Long) valueOfCell);
    } else if (valueOfCell instanceof Double) {
      cell.setCellValue((Double) valueOfCell);
    } else if (valueOfCell instanceof String) {
      cell.setCellValue((String) valueOfCell);
    } else {
      cell.setCellValue((Boolean) valueOfCell);
    }
    cell.setCellStyle(style);

  }

  private void write() {
    int rowCount = 1;
    CellStyle style = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    font.setFontHeight(14);
    style.setFont(font);
    for (LgIssue lgIssue : issues) {
      Row row = sheet.createRow(rowCount++);
      int columnCount = 0;
      createCell(row, columnCount++, lgIssue.getId(), style);
      createCell(row, columnCount++, lgIssue.getLgNumber(), style);
      createCell(row, columnCount++, lgIssue.getAmount(), style);
      createCell(row, columnCount++, lgIssue.getLgType(), style);
    }
  }

  public void generateExcelFile(HttpServletResponse response) throws IOException {
    writeHeader();
    write();
    ServletOutputStream outputStream = response.getOutputStream();
    workbook.write(outputStream);
    workbook.close();
    outputStream.close();
  }
}

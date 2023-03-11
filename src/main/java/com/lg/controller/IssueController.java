package com.lg.controller;

import com.lg.generator.CsvGenerator;
import com.lg.model.LgIssue;
import com.lg.generator.ExcelGenerator;
import com.lg.service.LgIssueService;
import com.lg.generator.PdfGenerator;
import com.lg.service.SecurityService;
import com.lg.service.UserService;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IssueController {

  @Autowired
  private UserService userService;

  @Autowired
  private SecurityService securityService;

  @Autowired
  private LgIssueService lgIssueService;

  @GetMapping("/")
  public String login(Model model, String error, String logout) {
    return "redirect:/issues";
  }

  @GetMapping({"/issues"})
  public String getAll(Model model,
      @RequestParam(required = false) String lgReference,
      @RequestParam(required = false) String lgType,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date issueDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
      @RequestParam(required = false) String iban,
      @RequestParam(required = false) String applicantCif,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "100") int size) {

    try {
      List<LgIssue> issues;
      Pageable paging = PageRequest.of(page - 1, size);

      Page<LgIssue> pageIssues;
      pageIssues = lgIssueService.findAll(lgReference, lgType, issueDate, startDate, endDate, iban,
          applicantCif, paging);
      issues = pageIssues.getContent();

      model.addAttribute("issues", issues);
      model.addAttribute("lgReference", lgReference);
      model.addAttribute("lgType", lgType);
      model.addAttribute("issueDate", issueDate);
      model.addAttribute("startDate", startDate);
      model.addAttribute("endDate", endDate);
      model.addAttribute("iban", iban);
      model.addAttribute("applicantCif", applicantCif);

      model.addAttribute("currentPage", pageIssues.getNumber() + 1);
      model.addAttribute("totalItems", pageIssues.getTotalElements());
      model.addAttribute("totalPages", pageIssues.getTotalPages());
      model.addAttribute("pageSize", size);
    } catch (Exception e) {
      model.addAttribute("message", e.getMessage());
    }
    return "issues";
  }

  @GetMapping("/issues/{id}")
  public String editTutorial(@PathVariable("id") Long id, Model model,
      RedirectAttributes redirectAttributes) {
    try {
      LgIssue issue = lgIssueService.findById(id);

      model.addAttribute("issue", issue);
      model.addAttribute("pageTitle", "Detail (ID: " + id + ")");
      return "issue_detail";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());

      return "redirect:/";
    }
  }

  @GetMapping({"/report"})
  public String getReport(Model model,
      @RequestParam(required = false) String lgReference,
      @RequestParam(required = false) String lgType,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date issueDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
      @RequestParam(required = false) String iban,
      @RequestParam(required = false) String applicantCif,
      HttpServletResponse response) {

    try {
      List<LgIssue> issues;
      Pageable paging = PageRequest.of(0, Long.SIZE);

      Page<LgIssue> pageIssues;
      pageIssues = lgIssueService.findAll(lgReference, lgType, issueDate, startDate, endDate, iban,
          applicantCif, paging);
      issues = pageIssues.getContent();

      model.addAttribute("issues", issues);
      model.addAttribute("lgReference", lgReference);
      model.addAttribute("lgType", lgType);
      model.addAttribute("issueDate", issueDate);
      model.addAttribute("startDate", startDate);
      model.addAttribute("endDate", endDate);
      model.addAttribute("iban", iban);
      model.addAttribute("applicantCif", applicantCif);

    } catch (Exception e) {
      model.addAttribute("message", e.getMessage());
    }
    return "report";
  }

  @GetMapping("/export-to-pdf")
  public void generatePdfFile(HttpServletResponse response)
      throws DocumentException, IOException {
    List<LgIssue> issues;
    Pageable paging = PageRequest.of(0, Long.SIZE);

    Page<LgIssue> pageIssues;
    pageIssues = lgIssueService.findAll(null, null, null, null, null, null,
        null, paging);
    issues = pageIssues.getContent();

    response.setContentType("application/pdf");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String currentDateTime = dateFormat.format(new Date());
    String headerkey = "Content-Disposition";
    String headervalue = "attachment; filename=report-issue-" + currentDateTime + ".pdf";
    response.setHeader(headerkey, headervalue);
    PdfGenerator generator = new PdfGenerator();
    generator.generate(issues, response);
  }

  @GetMapping("/export-to-excel")
  public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
    List<LgIssue> issues;
    Pageable paging = PageRequest.of(0, Long.SIZE);

    Page<LgIssue> pageIssues;
    pageIssues = lgIssueService.findAll(null, null, null, null, null, null,
        null, paging);
    issues = pageIssues.getContent();

    response.setContentType("application/octet-stream");
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    String currentDateTime = dateFormatter.format(new Date());

    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=report-issue-" + currentDateTime + ".xlsx";
    response.setHeader(headerKey, headerValue);

    ExcelGenerator generator = new ExcelGenerator(issues);
    generator.generateExcelFile(response);
  }

  @GetMapping("/export-to-csv")
  public void exportIntoCSV(HttpServletResponse response) throws IOException {
    Pageable paging = PageRequest.of(0, Long.SIZE);

    Page<LgIssue> pageIssues;
    pageIssues = lgIssueService.findAll(null, null, null, null, null, null,
        null, paging);
    List<LgIssue> issues = pageIssues.getContent();
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    String currentDateTime = dateFormatter.format(new Date());

    response.setContentType("text/csv");
    response.addHeader("Content-Disposition",
        "attachment;filename=report-issue-" + currentDateTime + ".csv");
    CsvGenerator csvGenerator = new CsvGenerator(issues);
    csvGenerator.generateCsvFile(response);
  }
}

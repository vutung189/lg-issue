package com.lg.controller;

import com.lg.generator.CsvGenerator;
import com.lg.generator.ExcelGenerator;
import com.lg.generator.PdfGenerator;
import com.lg.model.LgIssue;
import com.lg.service.LgIssueService;
import com.lg.utils.DateUtil;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@Controller
public class IssueController {

    @Autowired
    private LgIssueService lgIssueService;

    @GetMapping("/")
    public String issues(Model model, String error, String logout) {
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
                         @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size) {

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
    public String getReport() {
        return "report";
    }

    @GetMapping({"/export-report"})
    public ResponseEntity<InputStreamResource> exportReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date receiveFromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date receiveToDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date processFromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date processToDate,
            @RequestParam(required = false) String appStatus,
            @RequestParam(required = false) String crNumber,
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String customerCif,
            @RequestParam(required = false) String exportType,
            HttpServletResponse response) throws IOException, DocumentException {

        List<LgIssue> issues = lgIssueService.report(receiveFromDate, receiveToDate, processFromDate, processToDate, appStatus,
                crNumber, customerId, customerCif);
        if(!issues.isEmpty()) {
            if ("EXCEL".equals(exportType)) {
                exportIntoExcelFile(issues, response);
            } else if ("PDF".equals(exportType)) {
                PdfGenerator generator = new PdfGenerator();
                HttpHeaders headers = new HttpHeaders();
                headers.add("content-disposition", "inline;filename=report-issue-" + DateUtil.getDateTimeString(new Date()) + ".pdf");

                InputStreamResource resource = new InputStreamResource(generator.generate(issues,response));
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaType.parseMediaType("application/pdf"))
                        .body(resource);
            } else if ("CSV".equals(exportType)) {
                exportIntoCsvFile(issues, response);
            }
        } else {
            response.setContentType("text/plain; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            try {
                out.println("No issues found!");
            } finally {
                out.close();
            }
        }
        return null;
    }

    public void exportIntoPdfFile(List<LgIssue> issues, HttpServletResponse response)
            throws DocumentException, IOException {

        response.setContentType("application/pdf");
        String headerkey = "Content-Disposition";
        String headervalue = "inline; filename=report-issue-" + DateUtil.getDateTimeString(new Date()) + ".pdf";
        response.setHeader(headerkey, headervalue);
        PdfGenerator generator = new PdfGenerator();
        generator.generate(issues, response);
    }

    public void exportIntoExcelFile(List<LgIssue> issues, HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "inline; filename=report-issue-" + DateUtil.getDateTimeString(new Date()) + ".xlsx";
        response.setHeader(headerKey, headerValue);

        ExcelGenerator generator = new ExcelGenerator(issues);
        generator.generateExcelFile(response);
    }

    public void exportIntoCsvFile(List<LgIssue> issues, HttpServletResponse response) throws IOException {

        response.setContentType("text/csv");
        response.addHeader("Content-Disposition",
                "inline;filename=report-issue-" + DateUtil.getDateTimeString(new Date()) + ".csv");
        CsvGenerator csvGenerator = new CsvGenerator(issues);
        csvGenerator.generateCsvFile(response);
    }
}

package com.lg.controller;

import com.lg.model.LgIssue;
import com.lg.model.User;
import com.lg.service.LgIssueService;
import com.lg.service.SecurityService;
import com.lg.service.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

}

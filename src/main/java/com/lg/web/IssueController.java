package com.lg.web;

import com.lg.model.LgIssue;
import com.lg.service.LgIssueService;
import com.lg.service.SecurityService;
import com.lg.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

  @GetMapping({"/", "/issues"})
  public String getAll(Model model, @RequestParam(required = false) String keyword,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "100") int size) {

    try {
      List<LgIssue> issues = new ArrayList<LgIssue>();
      Pageable paging = PageRequest.of(page - 1, size);

      Page<LgIssue> pageIssues;
      pageIssues = lgIssueService.findAll(null, null, null, null, null, null, null, paging);
      issues = pageIssues.getContent();

      model.addAttribute("issues", issues);
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
  public String editTutorial(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
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

package com.lg.service;

import com.lg.model.LgIssue;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service class for managing users.
 */
public interface LgIssueService {

  Page<LgIssue> findAll(String lgReference,String lgType, Date issueDate, Date startDate,
      Date endDate, String iban, String applicantCif, Pageable pageable);

  LgIssue findById(Long id);

  List<LgIssue> report(Date receiveFromDate, Date receiveToDate, Date processFromDate, Date processToDate,
                       String appStatus, String crNumber, String customerId, String customerCif);
}

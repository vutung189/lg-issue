package com.lg.service;

import com.lg.model.LgIssue;
import com.lg.repository.LgIssueRepository;
import com.lg.utils.DateUtil;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LgIssueServiceImpl implements LgIssueService {

  private final LgIssueRepository lgIssueRepository;

  public LgIssueServiceImpl(LgIssueRepository lgIssueRepository) {
    this.lgIssueRepository = lgIssueRepository;
  }

  @Override
  public Page<LgIssue> findAll(String lgReference, String lgType, Date issueDate, Date startDate,
      Date endDate, String iban, String applicantCif, Pageable pageable) {
    if (endDate != null) {
      endDate = DateUtil.getEndOfDay(endDate);
    }
    return lgIssueRepository.findAll(lgReference, lgType, issueDate, startDate, endDate, iban,
        applicantCif, pageable);
  }

  @Override
  public LgIssue findById(Long id) {
    return lgIssueRepository.findById(id);
  }

  @Override
  public List<LgIssue> report(Date receiveFromDate, Date receiveToDate, Date processFromDate, Date processToDate,
                              String appStatus, String crNumber, String customerId, String customerCif) {
    if (receiveToDate != null) {
      receiveToDate = DateUtil.getEndOfDay(receiveToDate);
    }
    if (processToDate != null) {
      processToDate = DateUtil.getEndOfDay(processToDate);
    }
    return lgIssueRepository.report(receiveFromDate, receiveToDate, processFromDate, processToDate, appStatus,
            crNumber, customerId, customerCif);
  }
}

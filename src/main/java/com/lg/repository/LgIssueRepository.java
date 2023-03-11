package com.lg.repository;

import com.lg.model.LgIssue;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class LgIssueRepository {

  @PersistenceContext
  private EntityManager entityManager;

  public Page<LgIssue> findAll(String lgReference, String lgType, Date issueDate, Date startDate,
      Date endDate, String iban, String applicantCif, Pageable pageable) {
    StringBuilder hql = new StringBuilder("Select l from LgIssue l where 1=1 ");
    StringBuilder hqlCount = new StringBuilder("Select count(l.id) from LgIssue l where 1=1 ");

    if (lgReference != null && !lgReference.isEmpty()) {
      hql.append(" and l.lgNumber = :lgReference ");
      hqlCount.append(" and l.lgNumber = :lgReference ");
    }
    if (lgType != null && !lgType.isEmpty()) {
      hql.append(" and l.lgType = :lgType ");
      hqlCount.append(" and l.lgType = :lgType ");

    }
    if (issueDate != null && startDate == null && endDate == null) {
      hql.append(" and l.issueDate = :issueDate ");
      hqlCount.append(" and l.issueDate = :issueDate ");

    }
    if (startDate != null) {
      hql.append(" and l.issueDate >= :startDate ");
      hqlCount.append(" and l.issueDate >= :startDate ");

    }
    if (endDate != null) {
      hql.append(" and l.issueDate <= :endDate ");
      hqlCount.append(" and l.issueDate <= :endDate ");

    }
    if (iban != null && !iban.isEmpty()) {
      hql.append(" and l.appIban = :iban ");
      hqlCount.append(" and l.appIban = :iban ");

    }
    if (applicantCif != null && !applicantCif.isEmpty()) {
      hql.append(" and l.applicantCif = :applicantCif ");
      hqlCount.append(" and l.applicantCif = :applicantCif ");
    }

    hql.append(" ORDER BY l.issueDate ASC");

    TypedQuery<LgIssue> typedQuery = entityManager.createQuery(hql.toString(), LgIssue.class);
    Query countQuery = entityManager.createQuery(hqlCount.toString());

    if (lgReference != null && !lgReference.isEmpty()) {
      typedQuery.setParameter("lgReference", lgReference);
      countQuery.setParameter("lgReference", lgReference);

    }
    if (lgType != null && !lgType.isEmpty()) {
      typedQuery.setParameter("lgType", lgType);
      countQuery.setParameter("lgType", lgType);

    }
    if (issueDate != null && startDate == null && endDate == null) {
      typedQuery.setParameter("issueDate", issueDate);
      countQuery.setParameter("issueDate", issueDate);
    }
    if (startDate != null) {
      typedQuery.setParameter("startDate", startDate);
      countQuery.setParameter("startDate", startDate);
    }
    if (endDate != null) {
      typedQuery.setParameter("endDate", endDate);
      countQuery.setParameter("endDate", endDate);
    }
    if (iban != null && !iban.isEmpty()) {
      typedQuery.setParameter("iban", iban);
      countQuery.setParameter("iban", iban);
    }
    if (applicantCif != null && !applicantCif.isEmpty()) {
      typedQuery.setParameter("applicantCif", applicantCif);
      countQuery.setParameter("applicantCif", applicantCif);
    }

    typedQuery.setFirstResult((int) pageable.getOffset());
    typedQuery.setMaxResults(pageable.getPageSize());

    Long total = (Long) countQuery.getResultList().stream().findFirst().orElse(Long.valueOf(0));

    return new PageImpl<>(typedQuery.getResultList(), pageable, total);
  }

  public LgIssue findById(Long id) {
    return entityManager.find(LgIssue.class, id);
  }
}

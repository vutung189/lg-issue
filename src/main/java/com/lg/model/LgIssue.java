package com.lg.model;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "lg_issue")
public class LgIssue implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "req_id")
  private Long reqId;

  @Column(name = "arrival")
  private Date arrival;

  @Column(name = "entity_name", length = 100)
  private String entityName;

  @Column(name = "app_cr", length = 20)
  private String appCr;

  @Column(name = "app_iban", length = 26)
  private String appIban;
  @Column(name = "spec_iban", length = 26)
  private String specIban;

  @Column(name = "bank_fee", length = 26)
  private String bankFee;

  @Column(name = "app_id")
  private Long appId;

  @Column(name = "dob")
  private Date dob;

  @Column(name = "app_address", length = 100)
  private String appAddress;

  @Column(name = "contact_number", length = 30)
  private String contactNumber;

  @Column(name = "email_add", length = 128)
  private String emailAdd;

  @Column(name = "ben_name")
  private String benName;

  @Column(name = "ben_Id")
  private Long benId;
  @Column(name = "ben_address", length = 100)
  private String benAddress;

  @Column(name = "ben_contact", length = 30)
  private String benContact;

  @Column(name = "ben_uid", length = 20)
  private String benUid;
  @Column(name = "ben_email", length = 128)
  private String benEmail;

  @Column(name = "lg_number", length = 15)
  private String lgNumber;
  @Column(name = "lg_type", length = 3)
  private String lgType;
  @Column(name = "write_type", length = 30)
  private String writeType;
  @Column(name = "project_name")
  private String projectName;
  @Column(name = "project_no", length = 3)
  private String projectNo;
  @Column(name = "byan_no", length = 30)
  private String byanNo;
  @Column(name = "third_party", length = 100)
  private String thirdParty;
  @Column(name = "zakat_start")
  private Date zakatStart;
  @Column(name = "zakat_end")
  private Date zakatEnd;
  @Column(name = "purp_bond")
  private String purpBond;
  @Column(name = "start_date")
  private Date startDate;
  @Column(name = "end_date")
  private Date endDate;
  @Column(name = "issue_date")
  private Date issueDate;
  @Column(name = "hijri_start")
  private Date hijriStart;
  @Column(name = "hijri_end")
  private Date hijriEnd;
  @Column(name = "amount")
  private Double amount;
  @Column(name = "amount_text", length = 200)
  private String amountText;
  @Column(name = "assign_con", length = 5)
  private String assignCon;
  @Column(name = "terms_cond", columnDefinition="TEXT")
  private String termsCond;
  @Column(name = "currency", length = 10)
  private String currency;
  @Column(name = "open_ended", length = 5)
  private String openEnded;
  @Column(name = "auto_renewal", length = 5)
  private String autoRenewal;
  @Column(name = "trans_lg", length = 5)
  private String transLg;
  @Column(name = "assign_lg", length = 5)
  private String assignLg;
  @Column(name = "status", length = 30)
  private String status;
  @Column(name = "res_reason", length = 10)
  private String resReason;
  @Column(name = "status_time")
  private Date statusTime;
  @Column(name = "crd_file_name", length = 200)
  private String crdFileName;
  @Column(name = "crd_file_type", length = 4)
  private String crdFileType;
  @Lob
  @Column(name = "crd_attachment")
  private byte[] crd_attachment;
}

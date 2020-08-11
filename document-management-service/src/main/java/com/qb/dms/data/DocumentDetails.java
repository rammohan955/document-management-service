package com.qb.dms.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DocumentDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  private String userId;
  private String documentName;
  private String documentType;
  private String uploadDir;

  public DocumentDetails() { }

  public DocumentDetails(String userId, String fileName, String documentType, String uploadDir) {
    super();
    this.userId = userId;
    this.documentName = fileName;
    this.documentType = documentType;
    this.uploadDir = uploadDir;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getFileName() {
    return documentName;
  }

  public void setFileName(String fileName) {
    this.documentName = fileName;
  }

  public String getDocumentType() {
    return documentType;
  }

  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }

  public String getUploadDir() {
    return uploadDir;
  }

  public void setUploadDir(String uploadDir) {
    this.uploadDir = uploadDir;
  }

  public long getId() {
    return id;
  }

}

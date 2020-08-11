package com.qb.dms.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class DocumentPosts {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private long documentId;
  private String userId;
  private String postTitle;
  private String postContent;

  public DocumentPosts() { }

  public DocumentPosts(long documentId, String userId, String postTitle, String postContent) {
    super();
    this.documentId = documentId;
    this.userId = userId;
    this.postTitle = postTitle;
    this.postContent = postContent;
  }

  public long getDocumentId() {
    return documentId;
  }

  public void setDocumentId(long documentId) {
    this.documentId = documentId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPostTitle() {
    return postTitle;
  }

  public void setPostTitle(String postTitle) {
    this.postTitle = postTitle;
  }

  public String getPostContent() {
    return postContent;
  }

  public void setPostContent(String postContent) {
    this.postContent = postContent;
  }

  public long getId() {
    return id;
  }

}

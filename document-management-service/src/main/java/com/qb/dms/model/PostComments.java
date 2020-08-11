package com.qb.dms.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class PostComments {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private long postId;
  private String userId;
  private String commentTitle;
  private String commentContent;

  public PostComments() { }
  
  public PostComments(long postId, String userId, String commentTitle, String commentContent) {
    super();
    this.postId = postId;
    this.userId = userId;
    this.commentTitle = commentTitle;
    this.commentContent = commentContent;
  }

  public long getPostId() {
    return postId;
  }

  public void setPostId(long postId) {
    this.postId = postId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getCommentTitle() {
    return commentTitle;
  }

  public void setCommentTitle(String commentTitle) {
    this.commentTitle = commentTitle;
  }

  public String getCommentContent() {
    return commentContent;
  }

  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
  }

  public long getId() {
    return id;
  }

}

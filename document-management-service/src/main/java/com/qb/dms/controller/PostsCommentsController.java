package com.qb.dms.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.qb.dms.client.PostsCommentsServiceClient;
import com.qb.dms.model.DocumentPosts;
import com.qb.dms.model.PostComments;

@RestController
@RequestMapping(value = "/posts", produces = {MediaType.APPLICATION_JSON_VALUE})
public class PostsCommentsController {

  @Autowired
  PostsCommentsServiceClient postsServiceClient;

  @GetMapping("/")
  public ResponseEntity<List<DocumentPosts>> viewAllPosts() {
    return ResponseEntity.ok().body(postsServiceClient.getAllDocumentPost());
  }

  @PostMapping("/")
  public ResponseEntity<DocumentPosts> createPost(@RequestBody DocumentPosts post) {
    return ResponseEntity.ok().body(postsServiceClient.createDocumentPost(post));
  }

  @GetMapping("/{id}")
  public ResponseEntity<DocumentPosts> viewPost(@PathVariable(value = "id") long id) {
    return ResponseEntity.ok().body(postsServiceClient.getDocumentPost(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteDocumentPost(@PathVariable("id") long id,
      HttpServletRequest request) {
    return ResponseEntity.ok().body(postsServiceClient.deleteDocumentPost(id));
  }

  @GetMapping("/{id}/comments")
  public ResponseEntity<List<PostComments>> viewPostComments(@PathVariable(value = "id") long id) {
    return ResponseEntity.ok().body(postsServiceClient.getPostComments(id));
  }

  @PostMapping("/{id}/comments")
  public ResponseEntity<PostComments> createPostComments(@PathVariable(value = "id") long id,
      @RequestBody PostComments comment) {
    return ResponseEntity.ok().body(postsServiceClient.createPostComment(comment));
  }

  @GetMapping("/{id}/comments/{commentId}")
  public ResponseEntity<PostComments> viewPostCommentById(@PathVariable(value = "id") long id,
      @PathVariable(value = "commentId") long commentId) {
    return ResponseEntity.ok().body(postsServiceClient.getCommentById(commentId));
  }

  @DeleteMapping("/{id}/comments/{commentId}")
  public ResponseEntity<String> deletePostComment(@PathVariable(value = "id") long id,
      @PathVariable(value = "commentId") long commentId) {
    return ResponseEntity.ok().body(postsServiceClient.deletePostComment(commentId));
  }

}

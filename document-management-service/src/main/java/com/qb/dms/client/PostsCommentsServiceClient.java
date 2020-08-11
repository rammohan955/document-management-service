package com.qb.dms.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.qb.dms.model.DocumentPosts;
import com.qb.dms.model.PostComments;


@FeignClient(name = "post-comment", url = "https://my-json-server.typicode.com/rammohan955/demo/",
    configuration = ClientConfiguration.class)
public interface PostsCommentsServiceClient {

  @GetMapping("/posts")
  public List<DocumentPosts> getAllDocumentPost();

  @PostMapping("/posts")
  public DocumentPosts createDocumentPost(@RequestBody DocumentPosts post);

  @GetMapping("/posts/{id}")
  public DocumentPosts getDocumentPost(@PathVariable(value = "id") long id);

  @DeleteMapping("/posts/{id}")
  public String deleteDocumentPost(@PathVariable(value = "id") long id);

  @GetMapping("/posts/{postId}/comments")
  public List<PostComments> getPostComments(@PathVariable(value = "postId") long postId);

  @GetMapping("/comments")
  public List<PostComments> getAllComments();

  @PostMapping("/comments")
  public PostComments createPostComment(@RequestBody PostComments post);

  @GetMapping("/comments/{id}")
  public PostComments getCommentById(@PathVariable(value = "id") long id);

  @DeleteMapping("/comments/{id}")
  public String deletePostComment(@PathVariable(value = "id") long id);

  @GetMapping("/users/{userId}/posts/")
  public List<DocumentPosts> getDocumentPostsByUserId(
      @PathVariable(value = "userId") String userId);

  @GetMapping("/documents/{documentId}/posts/")
  public List<DocumentPosts> getDocumentPostsByDocumentId(
      @PathVariable(value = "documentId") String documentId);


}

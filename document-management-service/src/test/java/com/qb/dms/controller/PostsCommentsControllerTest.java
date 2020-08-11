package com.qb.dms.controller;

import java.io.IOException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qb.dms.DocumentManagementServiceApplication;
import com.qb.dms.model.DocumentPosts;
import com.qb.dms.model.PostComments;

@ContextConfiguration(classes = DocumentManagementServiceApplication.class)
@SpringBootTest(classes = PostsCommentsController.class)
@WebAppConfiguration
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class PostsCommentsControllerTest {

  @Autowired
  WebApplicationContext webApplicationContext;

  protected MockMvc mvc;

  @BeforeAll
  protected void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  protected String mapToJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }

  protected <T> T mapFromJson(String json, Class<T> clazz)
      throws JsonParseException, JsonMappingException, IOException {

    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(json, clazz);
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @Order(1)
  public void viewPosts() throws Exception {
    String uri = "/posts/";
    MvcResult mvcResult =
        mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

    int status = mvcResult.getResponse().getStatus();
    Assert.assertEquals(200, status);
    String content = mvcResult.getResponse().getContentAsString();
    DocumentPosts[] productlist = mapFromJson(content, DocumentPosts[].class);
    Assert.assertTrue(productlist.length > 0);
  }

  @Test
  @Order(2)
  public void createPost() throws Exception {
    String uri = "/posts/";
    DocumentPosts post = new DocumentPosts(1, "12345", "Test Post", "Test Content");
//    MvcResult mvcResult = ((ResultActions) ((MockHttpServletRequestBuilder) mvc
//        .perform(MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)))
//            .content(asJsonString(post))).andReturn();
    
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(post)) 
        .accept(MediaType.APPLICATION_JSON)).andReturn();
    int status = mvcResult.getResponse().getStatus();
    Assert.assertEquals(200, status);
  }

  @Test
  @Order(3)
  public void viewPostById() throws Exception {
    String uri = "/posts/1";
    MvcResult mvcResult =
        mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

    int status = mvcResult.getResponse().getStatus();
    Assert.assertEquals(200, status);
  }

  @Test
  @Order(4)
  public void deletePostById() throws Exception {
    String uri = "/posts/3";
    MvcResult mvcResult =
        mvc.perform(MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

    int status = mvcResult.getResponse().getStatus();
    Assert.assertEquals(200, status);
  }
  
  @Test
  @Order(5)
  public void viewPostComments() throws Exception {
    String uri = "/posts/1/comments";
    MvcResult mvcResult =
        mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

    int status = mvcResult.getResponse().getStatus();
    Assert.assertEquals(200, status);
    String content = mvcResult.getResponse().getContentAsString();
    PostComments[] productlist = mapFromJson(content, PostComments[].class);
    Assert.assertTrue(productlist.length > 0);
  }

  @Test
  @Order(6)
  public void createPostComment() throws Exception {
    String uri = "/posts/1/comments";
    PostComments comment = new PostComments(1, "12345", "Test Comment", "Test Content");
//    MvcResult mvcResult = ((ResultActions) ((MockHttpServletRequestBuilder) mvc
//        .perform(MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)))
//            .content(asJsonString(comment))).andReturn();
    
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(comment)) 
        .accept(MediaType.APPLICATION_JSON)).andReturn();
    int status = mvcResult.getResponse().getStatus();
    Assert.assertEquals(200, status);
  }

  @Test
  @Order(7)
  public void viewPostCommentById() throws Exception {
    String uri = "/posts/1/comments/1";
    MvcResult mvcResult =
        mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

    int status = mvcResult.getResponse().getStatus();
    Assert.assertEquals(200, status);
  }

  @Test
  @Order(8)
  public void deletePostCommentById() throws Exception {
    String uri = "/posts/1/comments/1";
    MvcResult mvcResult =
        mvc.perform(MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

    int status = mvcResult.getResponse().getStatus();
    Assert.assertEquals(200, status);
  }
}

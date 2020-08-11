package com.qb.dms.controller;

import java.io.File;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qb.dms.DocumentManagementServiceApplication;
import com.qb.dms.data.DocumentDetails;

@ContextConfiguration(classes = DocumentManagementServiceApplication.class)
@SpringBootTest(classes = DocumentController.class)
@WebAppConfiguration
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class DocumentControllerTest {

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

  @Test
  @Order(1)
  public void uploadDocument() throws Exception {
    String uri = "/123456/documents/upload";
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    String fileName = "test-doc.pdf";
    File file = new File(System.getProperty("user.dir") + "/documents/123456/" + fileName);
    MockMultipartFile mockMultipartFile = new MockMultipartFile("document", fileName,
        "application/pdf", "Document Upload Test".getBytes());
    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.multipart(uri).file(mockMultipartFile);
    this.mvc.perform(builder).andExpect(ok).andDo(MockMvcResultHandlers.print());;
    Assert.assertTrue(file.exists());
  }

  @Test
  @Order(2)
  public void getDocuments() throws Exception {
    String uri = "/123456/documents/view";
    MvcResult mvcResult =
        mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

    int status = mvcResult.getResponse().getStatus();
    Assert.assertEquals(200, status);
    String content = mvcResult.getResponse().getContentAsString();
    DocumentDetails[] productlist = mapFromJson(content, DocumentDetails[].class);
    Assert.assertTrue(productlist.length > 0);
  }

  @Test
  @Order(3)
  public void viewDocumentById() throws Exception {
    String uri = "/123456/documents/viewDocument/1";
    MvcResult result =
        mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_OCTET_STREAM))
            .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
    Assert.assertEquals(200, result.getResponse().getStatus());
    Assert.assertEquals("application/pdf", result.getResponse().getContentType());
  }
  
  @Test
  @Order(4)
  public void deleteDocumentById() throws Exception {
    String uri = "/123456/documents/delete/1";
    MvcResult mvcResult =
        mvc.perform(MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();
    
    int status = mvcResult.getResponse().getStatus();
    Assert.assertEquals(200, status);
    String fileName = "test-doc.pdf";
    File file = new File(System.getProperty("user.dir") + "/documents/123456/" + fileName);
    Assert.assertTrue(!file.exists());
  }
  
  @Test
  @Order(5)
  public void uploadDocumentInvalidType() throws Exception {
    String uri = "/123456/documents/upload";
    ResultMatcher badReq = MockMvcResultMatchers.status().isBadRequest();
    String fileName = "test-img.txt";
    MockMultipartFile mockMultipartFile = new MockMultipartFile("document", fileName,
        "text/plain", "Document Upload Test".getBytes());
    MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.multipart(uri).file(mockMultipartFile);
    this.mvc.perform(builder).andExpect(badReq).andDo(MockMvcResultHandlers.print());;
  }
  
  @Test
  @Order(6)
  public void viewDocumentByInvalidId() throws Exception {
    String uri = "/123456/documents/viewDocument/101";
    MvcResult result =
        mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_OCTET_STREAM))
            .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
    Assert.assertEquals(404, result.getResponse().getStatus());
  }
}

package com.qb.dms.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.qb.dms.data.DocumentDetails;
import com.qb.dms.repository.DocumentRepository;

@RestController
@RequestMapping(value = "{userId}/documents", produces = {MediaType.APPLICATION_JSON_VALUE})
public class DocumentController {
  
  private Logger logger = LoggerFactory.getLogger(DocumentController.class);

  @Autowired
  private DocumentRepository repository;

  private static final String MIME_APPLICATION_PDF = "application/pdf";
  private static final String DOC_SEPARATOR = "/";
  private static final String docsPath = System.getProperty("user.dir") + "/documents/";

  /**
   * @param userId
   * @return
   */
  @GetMapping("/view")
  public ResponseEntity<List<DocumentDetails>> viewAllDocuments(
      @PathVariable(value = "userId") String userId) {
    return ResponseEntity.ok().body(repository.findByUserId(userId));
  }

  /**
   * @param userId
   * @param multipartFile
   * @return
   */
  @PostMapping("/upload")
  public ResponseEntity<String> uploadDocument(@PathVariable("userId") String userId,
      @RequestParam("document") MultipartFile multipartFile) {

    if (multipartFile.getContentType().equalsIgnoreCase(MIME_APPLICATION_PDF)) {
      File uplodedDocument =
          new File(docsPath + userId + DOC_SEPARATOR + multipartFile.getOriginalFilename());
      try {
        if (!uplodedDocument.getParentFile().exists()) {
          logger.info("Creating Parent Directory for User");
          uplodedDocument.getParentFile().mkdirs();
        }
        if (uplodedDocument.exists()) {
          logger.info("Overriding existing file");
          uplodedDocument.createNewFile();
        }
        multipartFile.transferTo(uplodedDocument);
        DocumentDetails doc = new DocumentDetails(userId, uplodedDocument.getName(),
            MIME_APPLICATION_PDF, uplodedDocument.getPath());
        // TODO handle duplicate uploads if required
        logger.info("Saving Document Details to Repository");
        repository.save(doc);

      } catch (IllegalStateException | IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body("Document Upload Failed");
      }
      return ResponseEntity.ok().body("Document Uploaded successfully");
    } else {
      logger.info("Invalid File type while uploading docuement");
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * @param userId
   * @param id
   * @param request
   * @return
   */
  @GetMapping("/viewDocument/{id}")
  public ResponseEntity<Resource> downloadDocument(@PathVariable("userId") String userId,
      @PathVariable("id") long id, HttpServletRequest request) {
    logger.info("Download Document Starting");
    DocumentDetails doc = repository.findById(id);
    FileSystemResource resource = null;
    if (doc != null && doc.getFileName() != null && !doc.getFileName().isEmpty()) {
      try {
        resource = new FileSystemResource(doc.getUploadDir());
        if (!resource.exists()) {
          return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MIME_APPLICATION_PDF))
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
      } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).build();
      }
    } else {
      logger.info("Document not found");
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteDocument(@PathVariable("userId") String userId,
      @PathVariable("id") long id, HttpServletRequest request) {
    DocumentDetails doc = repository.findById(id);
    if (doc != null && doc.getFileName() != null && !doc.getFileName().isEmpty()) {
      try {
        File file = new File(doc.getUploadDir());
        logger.info("Deleting Document");
        if (!file.delete())
          return ResponseEntity.status(500).body("Delete Document Failed");
        repository.delete(doc);
        return ResponseEntity.ok().body("Document deleted successfully");
      } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).build();
      }
    } else {
      logger.info("Document not found");
      return ResponseEntity.notFound().build();
    }
  }
}

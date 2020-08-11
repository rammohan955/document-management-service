package com.qb.dms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DocumentManagementServiceApplication {
  private static Logger logger = LoggerFactory.getLogger(DocumentManagementServiceApplication.class);

  public static void main(String[] args) {
    logger.info("Staring Application");
    SpringApplication.run(DocumentManagementServiceApplication.class, args);
  }

}

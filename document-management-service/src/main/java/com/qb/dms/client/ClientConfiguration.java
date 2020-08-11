package com.qb.dms.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Retryer;

@Configuration
public class ClientConfiguration {
  
  /**
   * @return
   * 
   * define custom value for retry
   */
  @Bean
  Retryer retryer() {
    return new Retryer.Default();
  }
}

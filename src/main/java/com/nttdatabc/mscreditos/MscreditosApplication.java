package com.nttdatabc.mscreditos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Entrypoint.
 */
@SpringBootApplication
@EnableEurekaClient
public class MscreditosApplication {

  public static void main(String[] args) {
    SpringApplication.run(MscreditosApplication.class, args);
  }

}

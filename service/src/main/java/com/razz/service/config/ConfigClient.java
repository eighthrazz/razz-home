package com.razz.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// DELETE ME - THIS IS ONLY FOR TESTING!!!

@SpringBootApplication
@RestController
public class ConfigClient {
  public static void main(String[] args) {
    SpringApplication.run(ConfigClient.class, args);
  }
}

@RefreshScope
@RestController
class MessageRestController {

  @Autowired
  private Environment env;

  @Value("${service.garage.email.port}")
  private Integer emailPort;

  @RequestMapping("/test")
  String getMsg() {
    final StringBuilder text = new StringBuilder();
    text.append("service.garage.email.host=").append(env.getProperty("service.garage.email.host"));
    text.append("service.garage.email.port=").append(emailPort);
    return text.toString();
  }
}

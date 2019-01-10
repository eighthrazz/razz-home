package com.razz.service.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import com.mongodb.MongoClient;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

  @Value("service.mongo.host")
  private String mongoHost;

  @Value("service.mongo.port")
  private int mongoPort;

  public MongoConfig() {}

  @Override
  protected String getDatabaseName() {
    return "test";
  }

  @Override
  @Bean
  public MongoClient mongoClient() {
    return new MongoClient(mongoHost, mongoPort);
  }
}

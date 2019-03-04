package site.razz.common.spring.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

  @Value("${service.mongo.host}")
  private String mongoHost;

  @Value("${service.mongo.port}")
  private int mongoPort;

  @Value("${service.mongo.username}")
  private String mongoUser;

  @Value("${service.mongo.password}")
  private String mongoPassword;

  @Value("${service.mongo.database}")
  private String mongoDatabase;

  public MongoConfig() {}

  @Override
  protected String getDatabaseName() {
    return mongoDatabase;
  }

  @Override
  @Bean
  public MongoClient mongoClient() {
    final ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);
    final MongoCredential mongoCredential =
        MongoCredential.createCredential(mongoUser, mongoDatabase, mongoPassword.toCharArray());
    final MongoClientOptions mongoClientOptions =
        MongoClientOptions.builder().sslEnabled(false).socketTimeout(1500).build();
    return new MongoClient(serverAddress, mongoCredential, mongoClientOptions);
  }
}

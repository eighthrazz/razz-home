package com.razz.service.garage;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface NexxGarageEventRepository extends MongoRepository<NexxGarageEvent, String> {

  @Query("{ 'date' : ?0 }")
  boolean exists(long date);

}

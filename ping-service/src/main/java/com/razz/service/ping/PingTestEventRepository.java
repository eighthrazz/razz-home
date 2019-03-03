package com.razz.service.ping;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PingTestEventRepository extends MongoRepository<PingTestEvent, String> {

  @Query("{ 'date' : { $lt : ?0 } }")
  List<PingTestEvent> findByDateLessThan(long date);

}

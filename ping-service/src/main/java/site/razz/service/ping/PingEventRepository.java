package site.razz.service.ping;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import site.razz.common.spring.entity.PingEvent;

@Repository
public interface PingEventRepository extends MongoRepository<PingEvent, String> {

  @Query("{ 'date' : { $lt : ?0 } }")
  List<PingEvent> findByDateLessThan(long date);

  @Query("{ 'host' : { $eq : ?0 } }")
  List<PingEvent> findByHostOrderByDate(String host);
}

package site.razz.service.ping;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PingEventRepository extends MongoRepository<PingEvent, String> {

  @Query("{ 'date' : { $lt : ?0 } }")
  List<PingEvent> findByDateLessThan(long date);

}
